package com.traf.soulfragment.event;

import com.tfar.unstabletools.ObjectHolders;
import com.traf.soulfragment.item.ModItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
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
        InventoryCrafting craftingMatrix = (InventoryCrafting) event.craftMatrix;
        ItemStack out = event.crafting;

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
//                        stack.setCount(GuiScreen.isShiftKeyDown() ? 1 : 2);
//                        if (GuiScreen.isShiftKeyDown()) stack.grow(1);
                        craftingMatrix.setInventorySlotContents(i, stack.copy());
                    }
                }
            }

            double maxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
            if (maxHealth <= 6) {
                out.setCount(0);
                if (!world.isRemote) {
                    player.sendMessage(new TextComponentTranslation("soulfragment.soul_fragment.message"));
                }
                return;
            }
            if (craftingMatrix.getWidth() > 2 || craftingMatrix.getHeight() > 2) {
                out.setCount(0);
                return;
            }

            if (!world.isRemote) {
                IAttributeInstance maxHealthAttribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                if (maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID) != null) {
                    double value = maxHealthAttribute.getModifier(MAX_HEALTH_MODIFIER_ID).getAmount();
                    maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
                    maxHealthAttribute.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", -2 + value, 0));
                } else {
                    maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER_ID);
                    maxHealthAttribute.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", -2, 0));
                }
            }


        }


    }

}
