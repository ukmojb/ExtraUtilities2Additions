package com.wdcftgg.eu2a.proxy;

import com.rwtema.extrautils2.backend.ISidedFunction;
import com.wdcftgg.eu2a.event.CloneEvent;
import com.wdcftgg.eu2a.event.CraftingEvent;
import com.wdcftgg.eu2a.event.DropsEvent;
import com.wdcftgg.eu2a.event.EndDataSynchronismEvent;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {


	public void registerItemRenderer(Item item, int meta, String id) {
		//Ignored
	}

	public void onPreInit() {
	}

	public void onPostInit() {

	}

	public void onInit(){

		MinecraftForge.EVENT_BUS.register(new CraftingEvent());
		MinecraftForge.EVENT_BUS.register(new DropsEvent());
		MinecraftForge.EVENT_BUS.register(new CloneEvent());
		MinecraftForge.EVENT_BUS.register(new EndDataSynchronismEvent());

	}

	public <F, T> T apply(ISidedFunction<F, T> func, F input) {
		return func.applyServer(input);
	}

}
