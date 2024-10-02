package com.traf.soulfragment.recipe;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/16 21:50
 */

import com.tfar.unstabletools.UnstableTools;
import com.tfar.unstabletools.tools.ItemUnstableSword;
import com.traf.soulfragment.SoulFragmentMod;
import com.traf.soulfragment.item.ModItems;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;


public class CraftingLoader
{

    private static TIntSet usedHashes = new TIntHashSet();

    public static void init(IForgeRegistry<IRecipe> registry) {

        ItemUnstableSword unstableSword = new ItemUnstableSword(UnstableTools.UNSTABLE);
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.fromItem(unstableSword));


        RecipeSoulFragment recipe = new RecipeSoulFragment(SoulFragmentMod.MODID + "_" + ModItems.soulFragment.hashCode(),
                new ItemStack(ModItems.soulFragment),
                ingredients
        );

        registry.register(recipe);
    }


    public static void addShapedRecipe(ItemStack output, Object... params) {
        GameRegistry.addShapedRecipe(new ResourceLocation(SoulFragmentMod.MODID, getName(output.getItem())), (ResourceLocation)null, output, params);
    }

    public static void addRecipe(IRecipe recipe) {
        ForgeRegistries.RECIPES.register(recipe.setRegistryName(new ResourceLocation(SoulFragmentMod.MODID, getName(recipe.getRecipeOutput().getItem()))));
    }

    private static String getName(Item item) {
        int hash;
        for(hash = item.getRegistryName().hashCode(); usedHashes.contains(hash); ++hash) {
        }

        usedHashes.add(hash);
        return SoulFragmentMod.MODID + "_" + hash;
    }
}
