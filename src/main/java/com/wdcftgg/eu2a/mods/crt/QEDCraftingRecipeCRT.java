package com.wdcftgg.eu2a.mods.crt;

import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenClass("mods.eu2a.QEDCraftingRecipeCRT")
@SuppressWarnings("unused")
public abstract class QEDCraftingRecipeCRT {

    @ZenMethod
    public static void addRecipe(int craftTime, IItemStack output, IIngredient[][] ingredients) {
        List<ItemStack> list = new ArrayList<>();
        for (IIngredient[] ingredients1 : ingredients) {
            for (int j = 0; j < ingredients.length; j++) {
                IIngredient ingredient = ingredients1[j];
                list.add(CraftTweakerMC.getItemStack(ingredient));
            }
        }

        if (list.size() == 9) {
            QEDCraftingRecipeManager.addRecipe(list, CraftTweakerMC.getItemStack(output), craftTime);
        } else {
            CraftTweakerAPI.logError("QED recipe error check your script");
        }

    }
}
