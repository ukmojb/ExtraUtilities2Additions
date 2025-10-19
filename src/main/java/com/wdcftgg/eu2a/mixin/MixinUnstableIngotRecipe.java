package com.wdcftgg.eu2a.mixin;

import com.google.common.collect.ImmutableList;
import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.crafting.CraftingHelper;
import com.rwtema.extrautils2.items.ItemUnstableIngots;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemUnstableIngots.class, remap = false)
public class MixinUnstableIngotRecipe {

    /**
     * @author 111
     * @reason 111
     */
    @Overwrite
    public void addRecipes() {
//        CraftingHelper.addRecipe(new ItemUnstableIngots.UnstableIngotRecipe(CraftingHelper.createLocation("unstable_ingot")));
//        CraftingHelper.addShaped("unstable_nugget", XU2Entries.unstableIngots.newStack(1, 1), new Object[]{"i", "d", "s", 'i', "nuggetIron", 'd', "stickWood", 's', "gemDiamond"});
//        CraftingHelper.addShaped("stable_unstable_ingot", XU2Entries.unstableIngots.newStack(1, 2), new Object[]{"nnn", "nnn", "nnn", 'n', XU2Entries.unstableIngots.newStack(1, 1)});
//        CraftingHelper.addShapeless("unstable_unpack", XU2Entries.unstableIngots.newStack(9, 1), new Object[]{XU2Entries.unstableIngots.newStack(1, 2)});
    }
}
