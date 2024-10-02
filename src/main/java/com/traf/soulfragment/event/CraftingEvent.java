package com.traf.soulfragment.event;

import com.tfar.unstabletools.ObjectHolders;
import com.traf.soulfragment.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class CraftingEvent {
    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        InventoryCrafting craftingMatrix = (InventoryCrafting) event.craftMatrix;

        if (event.crafting.getItem().equals(ModItems.soulFragment)) {
            boolean hasSword = false;
            for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
                ItemStack stack = craftingMatrix.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    if (stack.getItem().equals(Items.AIR) || stack.getItem().equals(ObjectHolders.unstableSword)) {
                        hasSword = stack.getItem().equals(ObjectHolders.unstableSword);
                    }
                }
            }
            if (!hasSword) {
                return;
            }
        }

        for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
            ItemStack stack = craftingMatrix.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stack.grow(1);
                craftingMatrix.setInventorySlotContents(i, stack.copy());
            }
        }

        if (craftingMatrix.getWidth() > 2 || craftingMatrix.getHeight() > 2) {
            event.crafting.setCount(0);
        }

    }

}
