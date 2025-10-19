package com.wdcftgg.eu2a.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static com.wdcftgg.eu2a.item.ModItems.ITEMS;

public class ItemWateringCan extends Item implements IHasModel {

    public ItemWateringCan() {
        setMaxStackSize(1);
        setTranslationKey("watering_can");
        setRegistryName("watering_can");
        this.setCreativeTab(ExtraUtilities2Additions.creativeTab);

        ITEMS.add(this);
    }


    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return hasWater(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (player.isBurning() && hasWater(stack)) {
            player.extinguish();
            world.playSound(null, player.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        RayTraceResult ray = this.rayTrace(world, player, true);
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = ray.getBlockPos();
            IBlockState state = world.getBlockState(pos);

            if (!hasWater(stack)) {
                if (state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0) {
                    world.playSound(player, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    player.swingArm(hand);
                    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    // 用于作物/火焰/末影人等逻辑，优先执行并阻止草块的骨粉效果等
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos,
                                           EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!hasWater(stack)) return EnumActionResult.PASS;

        doEffects(world, player, pos);
        tryDrain(stack, world);

        if (!world.isRemote) player.swingArm(hand);
        return EnumActionResult.SUCCESS;
    }

    private void doEffects(World world, EntityPlayer player, BlockPos center) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                BlockPos pos = center.add(dx, 0, dz);
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();

                // 粒子效果（客户端）
                if (world.isRemote) {
                    world.spawnParticle(EnumParticleTypes.WATER_SPLASH,
                            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                            0, 0.1, 0);
                }

                // 作物加速（使用骨粉机制）
                if (!world.isRemote && block instanceof IGrowable) {
                    IGrowable growable = (IGrowable) block;
                    if (growable.canGrow(world, pos, state, false) &&
                            growable.canUseBonemeal(world, world.rand, pos, state)) {
                        growable.grow(world, world.rand, pos, state);
                    }
                }

                // 草传播
                if (!world.isRemote && (block == Blocks.GRASS || block == Blocks.MYCELIUM)) {
                    for (EnumFacing face : EnumFacing.HORIZONTALS) {
                        BlockPos nearby = pos.offset(face);
                        IBlockState neighbor = world.getBlockState(nearby);
                        if (neighbor.getBlock() == Blocks.DIRT) {
                            world.setBlockState(nearby, block.getDefaultState());
                        }
                    }
                }

                // 扑灭火焰
                if (!world.isRemote && block == Blocks.FIRE) {
                    world.setBlockToAir(pos);
                    world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                // 岩浆流速加快
                if (!world.isRemote && state.getMaterial() == Material.LAVA && state.getValue(BlockLiquid.LEVEL) > 0) {
                    world.scheduleUpdate(pos, block, 1);
                }
            }
        }

        // 末影人伤害
        if (!world.isRemote) {
            List<EntityEnderman> endermen = world.getEntitiesWithinAABB(EntityEnderman.class,
                    player.getEntityBoundingBox().grow(3.0));
            for (EntityEnderman e : endermen) {
                e.attackEntityFrom(DamageSource.GENERIC, 1.0F);
            }
        }
    }

    private void tryDrain(ItemStack stack, World world) {
    }

    private boolean hasWater(ItemStack stack) {
        return true;
    }

    @Override
    public void registerModels() {
        ExtraUtilities2Additions.proxy.registerItemRenderer(this, 0, "inventory");
    }
}