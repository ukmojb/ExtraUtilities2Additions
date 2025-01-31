package com.traf.soulfragment.recipe;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/16 21:50
 */

import com.traf.soulfragment.SoulFragmentMod;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;


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
