package com.wdcftgg.eu2a.mods.jei.qed;

import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipe;
import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipeManager;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeMaker {
    public static List<QEDRecipe> getFishingConditionRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
//        SandingRecipes instance = SandingRecipes.instance;
//
//        Map<ItemStack, ItemStack> recipeMap = instance.recipes;
        List<QEDRecipe> recipeList = new ArrayList<>();

        for (QEDCraftingRecipe recipe : QEDCraftingRecipeManager.getAllRecipes()) {
            recipeList.add(new QEDRecipe(recipe));
        }
//
//        for (Map.Entry<ItemStack, ItemStack> entry : recipeMap.entrySet()) {
//            recipeList.add(new SandingRecipe(entry.getKey(), entry.getValue()));
//        }

        return recipeList;
    }
}
