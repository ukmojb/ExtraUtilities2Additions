package com.wdcftgg.eu2a.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static com.wdcftgg.eu2a.item.ModItems.ITEMS;

public class ItemUnstableNugget extends Item implements IHasModel {
    public ItemUnstableNugget() {
        setTranslationKey("unstable_nugget");
        setRegistryName("unstable_nugget");
        this.setCreativeTab(ExtraUtilities2Additions.creativeTab);

        ITEMS.add(this);
    }


    @Override
    @Nonnull
    public ItemStack getContainerItem(ItemStack itemStack) {
        return this.getDefaultInstance();
    }

    @Override
    public void registerModels() {
        ExtraUtilities2Additions.proxy.registerItemRenderer(this, 0, "inventory");
    }
}