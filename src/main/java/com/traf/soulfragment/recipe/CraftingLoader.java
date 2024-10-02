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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import static com.tfar.unstabletools.UnstableTools.UNSTABLE;


public class CraftingLoader
{

    private static TIntSet usedHashes = new TIntHashSet();

    public static void init(IForgeRegistry<IRecipe> registry) {

//        addRecipe(new RecipeSoulFragment());
        RecipeSoulFragment recipe = new RecipeSoulFragment();
        recipe.setRegistryName(new ResourceLocation(SoulFragmentMod.MODID, "craft_soulfragment"));
        registry.register(recipe);

    }

    public static void addRecipe(IRecipe recipe) {
        recipe.setRegistryName(new ResourceLocation(SoulFragmentMod.MODID, "craft_soulfragment"));
        ForgeRegistries.RECIPES.register(recipe);
    }

    private static String getName(Item item) {
        int hash;
        for(hash = item.getRegistryName().hashCode(); usedHashes.contains(hash); ++hash) {
        }

        usedHashes.add(hash);
        return SoulFragmentMod.MODID + "_" + hash;
    }
}
