package com.wdcftgg.eu2a.mods.jei.qed;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.mods.jei.JEICompat;
import com.wdcftgg.eu2a.mods.unstabletools.ObjectHolders;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class QEDCategory implements IRecipeCategory {

    private final IDrawable background;
    private final IDrawable icon;


    public QEDCategory(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation(ExtraUtilities2Additions.MODID, "textures/gui/jei.png");
        this.background = helper.drawableBuilder(location, 0, 0, 174,81).setTextureSize(174,81).build();
//        background = helper.createBlankDrawable(162,126);
        icon = helper.createDrawableIngredient(new ItemStack(ObjectHolders.qed));
    }

    @Override
    public String getUid() {
        return JEICompat.QED;
    }

    @Override
    public String getTitle() {
        return "QED";
    }

    @Override
    public String getModName() {
        return ExtraUtilities2Additions.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper iRecipeWrapper, IIngredients iIngredients) {

        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

        List<List<ItemStack>> inputs = iIngredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = iIngredients.getOutputs(VanillaTypes.ITEM);

        int id = 0;

//        stacks.init(id++, true, 0, -2);
//        stacks.init(id++, true, 18, -2);
//        stacks.init(id++, true, 36, -2);
//        stacks.init(id++, true, 54, -2);
//        stacks.init(id++, true, 72, -2);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int index = row * 3 + col;
                stacks.init(id++, true, 28 + col * 18, 15 + row * 18);
//                addSlotToContainer(new SlotItemHandler(handler, index, 31 + col * 18, 17 + row * 18));
            }
        }

        stacks.init(id++, false, 122, 33);

        stacks.set(iIngredients);
    }

//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
//        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
//        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
//        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
//        //Output
//        int offset1 = outputs.size() - 1;
//        for (int i = 0; i <= offset1; i++) guiItemStacks.init(i, false, 102 + (offset1 - i) * 17, 9);
//        //Input
//        int offset0 = inputs.size() - 1;
//        for (int i = 0; i <= offset0; i++) guiItemStacks.init(i + 5, true, 56 - (offset0 - i) * 17, 9);
//
//        guiItemStacks.set(ingredients);
//    }
}