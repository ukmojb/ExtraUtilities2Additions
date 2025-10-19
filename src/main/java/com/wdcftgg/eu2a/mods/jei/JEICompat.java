package com.wdcftgg.eu2a.mods.jei;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.item.ModItems;
import com.wdcftgg.eu2a.mods.jei.qed.QEDCategory;
import com.wdcftgg.eu2a.mods.jei.qed.RecipeMaker;
import com.wdcftgg.eu2a.mods.unstabletools.ObjectHolders;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@JEIPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class JEICompat implements IModPlugin {

    public static final String QED = ExtraUtilities2Additions.MODID + ".qed";


    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IJeiHelpers helpers = registry.getJeiHelpers();

        final IGuiHelper gui = helpers.getGuiHelper();

        registry.addRecipeCategories(new QEDCategory(gui));
    }

    @Override
    public void register(IModRegistry registry) {
        final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

        registry.addRecipes(RecipeMaker.getFishingConditionRecipes(jeiHelpers), QED);

        registry.addRecipeCatalyst(new ItemStack(ObjectHolders.qed), new String[]{QED});

        List<IRecipe> vanillaRecipes = new ArrayList<>();
        vanillaRecipes.add(new ShapedOreRecipe(
                new ResourceLocation(ExtraUtilities2Additions.MODID, "soulfragment_recipe"),
                new ItemStack(ModItems.soulFragment),
                "Y",
                'Y', ObjectHolders.unstableSword
        ));

        registry.addRecipes(vanillaRecipes, VanillaRecipeCategoryUid.CRAFTING);

    }

}