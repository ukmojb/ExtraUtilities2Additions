package com.traf.soulfragment.event;

import com.traf.soulfragment.util.Tools;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.traf.soulfragment.item.ItemSoulFragment.MAX_HEALTH_MODIFIER_ID;


@Mod.EventBusSubscriber
public class EndDataSynchronismEvent {


    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;

        if (!event.isEndConquered()) return;

        System.out.println("dadwdac");


        IAttributeInstance maxHealthAttribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        if (Tools.getHealthNBT(player, MAX_HEALTH_MODIFIER_ID) != -114514) {

            maxHealthAttribute.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "Health boost", Tools.getHealthNBT(player, MAX_HEALTH_MODIFIER_ID), 0));
        }
    }
}
