package com.wdcftgg.eu2a.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.mods.unstabletools.item.IItemColored;
import com.wdcftgg.eu2a.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.wdcftgg.eu2a.item.ModItems.ITEMS;

public class ItemPseudoInversionSigil extends Item implements IHasModel, IItemColored {
    public ItemPseudoInversionSigil() {
        setTranslationKey("pseudo_inversion_sigil");
        setRegistryName("pseudo_inversion_sigil");
        this.setCreativeTab(ExtraUtilities2Additions.creativeTab);
        this.maxStackSize = 1;

        ITEMS.add(this);
    }


    @Override
    @Nonnull
    public ItemStack getContainerItem(ItemStack itemStack) {
        return this.getDefaultInstance();
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return 0xee0000;
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