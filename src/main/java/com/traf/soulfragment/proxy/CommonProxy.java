package com.traf.soulfragment.proxy;

import com.rwtema.extrautils2.backend.ISidedFunction;
import com.traf.soulfragment.event.CloneEvent;
import com.traf.soulfragment.event.CraftingEvent;
import com.traf.soulfragment.event.DropsEvent;
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
	}

	public <F, T> T apply(ISidedFunction<F, T> func, F input) {
		return func.applyServer(input);
	}

}
