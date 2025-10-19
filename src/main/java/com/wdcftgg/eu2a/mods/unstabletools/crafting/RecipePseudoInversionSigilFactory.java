package com.wdcftgg.eu2a.mods.unstabletools.crafting;

import com.google.gson.JsonObject;
import com.wdcftgg.eu2a.item.ItemPseudoInversionSigil;
import com.wdcftgg.eu2a.mods.unstabletools.UnstableTools;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipePseudoInversionSigilFactory implements IRecipeFactory {
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);

        CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
        primer.width = recipe.getRecipeWidth();
        primer.height = recipe.getRecipeHeight();
        primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
        primer.input = recipe.getIngredients();

        return new PseudoInversionSigilRecipe(new ResourceLocation(UnstableTools.MODID, "mobius_ingot_crafting"), recipe.getRecipeOutput(), primer);
    }

    public static class PseudoInversionSigilRecipe extends ShapedOreRecipe {
        public PseudoInversionSigilRecipe(ResourceLocation group, ItemStack result, CraftingHelper.ShapedPrimer primer) {
            super(group, result, primer);
        }

        @Override
        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
            // 返回原物品（不消耗）
            NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
            for (int i = 0; i < remaining.size(); i++) {
                if (inv.getStackInSlot(i).getItem() instanceof ItemPseudoInversionSigil) {
                    remaining.set(i, inv.getStackInSlot(i).copy());
                }
            }
            return remaining;
        }
    }
}