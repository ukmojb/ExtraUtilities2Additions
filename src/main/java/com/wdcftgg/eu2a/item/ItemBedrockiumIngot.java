package com.wdcftgg.eu2a.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.util.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static com.wdcftgg.eu2a.item.ModItems.ITEMS;

public class ItemBedrockiumIngot extends Item implements IHasModel {
    public ItemBedrockiumIngot() {
        setTranslationKey("bedrockiumIngot");
        setRegistryName("bedrockiumIngot");
        this.setCreativeTab(ExtraUtilities2Additions.creativeTab);

        ITEMS.add(this);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 3));
        }
    }

    @Override
    public void registerModels() {
        ExtraUtilities2Additions.proxy.registerItemRenderer(this, 0, "inventory");
    }
}