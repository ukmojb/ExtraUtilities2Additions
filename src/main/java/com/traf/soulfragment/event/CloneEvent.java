package com.traf.soulfragment.event;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.traf.soulfragment.item.ItemSoulFragment.MAX_HEALTH_MODIFIER_ID;

@Mod.EventBusSubscriber
public class CloneEvent {

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        EntityPlayer original = event.getOriginal();
        EntityPlayer cloned = event.getEntityPlayer();

        IAttributeInstance maxHealthAttributeOriginal = original.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        if (maxHealthAttributeOriginal.getModifier(MAX_HEALTH_MODIFIER_ID) != null) {
            double modifierValue = maxHealthAttributeOriginal.getModifier(MAX_HEALTH_MODIFIER_ID).getAmount();
            IAttributeInstance maxHealthAttributeCloned = cloned.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            maxHealthAttributeCloned.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", modifierValue, 0));
        }
    }
}
