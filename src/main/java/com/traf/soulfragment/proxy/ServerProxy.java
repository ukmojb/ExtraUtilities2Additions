package com.traf.soulfragment.proxy;


import com.traf.soulfragment.event.CloneEvent;
import com.traf.soulfragment.event.CraftingEvent;
import com.traf.soulfragment.event.DropsEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {


    public ServerProxy() {
    }

    public void onPreInit() {
        super.onPreInit();
    }

    public void onPostInit() {
        super.onPostInit();
    }

    public void onInit(){
        super.onInit();
        MinecraftForge.EVENT_BUS.register(new CraftingEvent());
        MinecraftForge.EVENT_BUS.register(new DropsEvent());
        MinecraftForge.EVENT_BUS.register(new CloneEvent());
    }
}
