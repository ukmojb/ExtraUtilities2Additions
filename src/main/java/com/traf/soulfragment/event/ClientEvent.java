package com.traf.soulfragment.event;

import com.traf.soulfragment.network.MessageOpenInventory;
import com.traf.soulfragment.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ClientEvent {

    public static boolean oponIn = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().currentScreen != null) {
            ClientEvent.oponIn = Minecraft.getMinecraft().currentScreen instanceof GuiInventory;
        } else {
            ClientEvent.oponIn = false;
        }
        PacketHandler.INSTANCE.sendToServer(new MessageOpenInventory(ClientEvent.oponIn));
    }
}
