package com.wdcftgg.eu2a.mods.unstabletools.recipe;

import net.minecraft.item.ItemStack;

import java.util.List;

public class QEDCraftingRecipe {

    private final List<ItemStack> input; // size 9
    private final ItemStack output;
    private final int craftTime; // in ticks

    public QEDCraftingRecipe(List<ItemStack> input, ItemStack output, int craftTime) {
        if (input.size() != 9)
            throw new IllegalArgumentException("Input must be size 9 for a 3x3 grid");
        this.input = input;
        this.output = output;
        this.craftTime = craftTime;
    }

    public List<ItemStack> getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getCraftTime() {
        return craftTime;
    }

    public boolean matches(List<ItemStack> inv) {
        if (inv.size() != 9) return false;
        for (int i = 0; i < 9; i++) {
            ItemStack a = inv.get(i);
            ItemStack b = input.get(i);
            if (!ItemStack.areItemsEqual(a, b) && !(a.isEmpty() && b.isEmpty())) {
                return false;
            }
        }
        return true;
    }
}
