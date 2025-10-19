package com.wdcftgg.eu2a.mods.unstabletools.block.te;

import net.minecraft.block.BlockDirectional;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityConveyorBelt extends TileEntity implements ITickable {
    private static final double MOVE_SPEED = 0.1; // 移动速度

    @Override
    public void update() {
        if (world.isRemote || world.getTotalWorldTime() % 2 != 0) return;

        EnumFacing facing = world.getBlockState(pos).getValue(BlockDirectional.FACING).getOpposite();
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(0.5, 0.1, 0.5).offset(0, 1, 0);

        for (EntityLiving entity : world.getEntitiesWithinAABB(EntityLiving.class, area)) {
            // 计算推动向量
            double motionX = facing.getXOffset() * MOVE_SPEED;
            double motionZ = facing.getZOffset() * MOVE_SPEED;

            // 应用运动（保持原有Y轴速度）
            entity.motionX = motionX;
            entity.motionZ = motionZ;
            entity.velocityChanged = true;

            // 调整生物朝向（可选）
            entity.rotationYaw = facing.getHorizontalAngle();
        }
    }
}