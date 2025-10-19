package com.wdcftgg.eu2a.mods.unstabletools;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.mods.unstabletools.item.IItemColored;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class Client {
  @SubscribeEvent
  public static void colors(ColorHandlerEvent.Item e) {
    ExtraUtilities2Additions.MOD_ITEMS.stream().filter(item -> item instanceof IItemColored).forEach(item -> e.getItemColors().registerItemColorHandler(((IItemColored) item)::getColor, item));
  }
}

