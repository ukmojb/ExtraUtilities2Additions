package com.traf.soulfragment.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RecipeSoulFragment extends ShapelessRecipes {

    private final ItemStack result;

    public RecipeSoulFragment(String group, ItemStack output, NonNullList<Ingredient> ingredients) {
        super(group, output, ingredients);
        this.result = output;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return result.copy();
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack item = inv.getStackInSlot(i);
            if (!item.isEmpty()) {
                items.add(item);
            }
        }

        // 检查是否有合适的物品
        for (Ingredient recipeIngredient : this.getIngredients()) {
            boolean found = false;
            for (ItemStack itemStack : items) {
                if (recipeIngredient.test(itemStack)) {
                    found = true;
//                    items.remove(itemStack);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height <= 2;
    }
}
