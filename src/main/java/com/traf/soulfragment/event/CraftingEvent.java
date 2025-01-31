package com.traf.soulfragment.event;

import com.tfar.unstabletools.ObjectHolders;
import com.traf.soulfragment.item.ModItems;
import com.traf.soulfragment.util.Tools;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.traf.soulfragment.item.ItemSoulFragment.MAX_HEALTH_MODIFIER_ID;

@Mod.EventBusSubscriber
public class CraftingEvent {


    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;
        IInventory crafting =  event.craftMatrix;
        ItemStack out = event.crafting;

        if (crafting instanceof InventoryCrafting) {
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

                for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
                    ItemStack stack = craftingMatrix.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        if (stack.getItem().equals(ObjectHolders.unstableSword)) {
                            craftingMatrix.setInventorySlotContents(i, stack.copy());
                        }
                    }
                }

                IAttributeInstance maxHealthAttribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                AttributeModifier addmodifier;
                if (maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID) != null) {
                    double value = maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID).getAmount();
                    maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
                    addmodifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", -2 + value, 0);
                    maxHealthAttribute.applyModifier(addmodifier);
                } else {
                    maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
                    addmodifier = new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", -2, 0);
                    maxHealthAttribute.applyModifier(addmodifier);
                }

                Tools.saveHealthNBT(player, addmodifier.getAmount(), MAX_HEALTH_MODIFIER_ID);

            }
        }


    }


}
