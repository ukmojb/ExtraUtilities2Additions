package com.wdcftgg.eu2a.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.wdcftgg.eu2a.item.ModItems.ITEMS;

public class ItemMobiusIngot extends Item implements IHasModel {
    public ItemMobiusIngot() {
        setTranslationKey("mobius_ingot");
        setRegistryName("mobius_ingot");
        this.setCreativeTab(ExtraUtilities2Additions.creativeTab);

        ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public void registerModels() {
        ExtraUtilities2Additions.proxy.registerItemRenderer(this, 0, "inventory");
    }
}