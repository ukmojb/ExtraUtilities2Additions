package com.wdcftgg.eu2a.mods.unstabletools.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QEDCraftingRecipeManager {

    private static final List<QEDCraftingRecipe> RECIPES = new ArrayList<>();

    public static void addRecipe(List<ItemStack> input, ItemStack output, int craftTime) {
        RECIPES.add(new QEDCraftingRecipe(input, output, craftTime));
    }
    public static void addRecipe(int craftTime, ItemStack output, ItemStack... input) {
        RECIPES.add(new QEDCraftingRecipe(Arrays.asList(input), output, craftTime));
    }

    public static QEDCraftingRecipe findMatchingRecipe(List<ItemStack> input) {
        for (QEDCraftingRecipe recipe : RECIPES) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<QEDCraftingRecipe> getAllRecipes() {
        return RECIPES;
    }
}