package com.wdcftgg.eu2a.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBlockBedrockiumBlock extends ItemBlock {
    public ItemBlockBedrockiumBlock(Block block) {
        super(block);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 3));
        }
    }

}
