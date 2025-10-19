package com.wdcftgg.eu2a.mods.jei.qed;

import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QEDRecipe implements IRecipeWrapper {

    public QEDCraftingRecipe recipe;

    public QEDRecipe(QEDCraftingRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {


        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInput());

        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
//        ingredients.setInputLists(VanillaTypes.ITEM, in);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        if (info == null) return;
//
//        String text = I18n.format(info);
//
//        int width = minecraft.fontRenderer.getStringWidth(text);
//        int x = (int) ((recipeWidth - width) + 25);
//        int y = 4;
//
//        minecraft.fontRenderer.drawString(text, x - 30, y, Color.BLACK.getRGB());

    }

    public static List<LootPool> getPools(LootTable table) {
        try
        {
            Field field = ReflectionHelper.findField(LootTable.class, "pools", "field_186466_c");
            List<LootPool> pools = (List<LootPool>) field.get(table);
            return pools;
        }
        catch (Exception e)
        {
            return null;
//            throw new RuntimeException(e);
        }
    }

}
