package com.traf.soulfragment.recipe;

import com.tfar.unstabletools.ObjectHolders;
import com.traf.soulfragment.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class RecipeSoulFragment extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private int width = 0;
    private int height = 0;


    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = inv.getStackInSlot(j);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem().getRegistryName().toString().equals("unstabletools:unstable_sword"))
                {
                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.AIR)
                    {
                        return false;
                    }
                }
            }
        }
        return !itemstack.isEmpty();
    }


    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = inv.getStackInSlot(j);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem().getRegistryName().toString().equals("unstabletools:unstable_sword"))
                {
                    if (!itemstack.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.AIR)
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (this.width > 2 || this.height > 2) return ItemStack.EMPTY;

        if (Minecraft.getMinecraft().player.getMaxHealth() > 6) {
            return new ItemStack(ModItems.soulFragment);
        } else {
            return ItemStack.EMPTY;
        }
    }


    public boolean canFit(int width, int height) {
        this.width = width;
        this.height = height;
        return width == 2 && height == 2;
    }


    public ItemStack getRecipeOutput() {
        return Items.APPLE.getDefaultInstance();
    }


    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

//        for (int i = 0; i <= inv.getSizeInventory(); i++) {
//            ItemStack stack = inv.getStackInSlot(i);
//                ItemStack itemStack = ObjectHolders.unstableSword.getDefaultInstance().copy();
//            if (input.apply(stack)) {
//                remaining.set(i, itemStack);
//            } else {
//                remaining.set(i, itemStack);
//            }
//        }
//        remaining.add(0, Items.APPLE.getDefaultInstance());
//        remaining.add(1, Items.ARROW.getDefaultInstance());

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack.getItem().getRegistryName().toString().equals("unstabletools:unstable_sword"))
            {
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                nonnulllist.set(i, itemstack1);
                break;
            }
        }

        return nonnulllist;
    }
}
