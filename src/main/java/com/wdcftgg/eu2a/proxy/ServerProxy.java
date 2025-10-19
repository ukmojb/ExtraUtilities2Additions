package com.wdcftgg.eu2a.proxy;


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
//        MinecraftForge.EVENT_BUS.register(new CraftingEvent());
//        MinecraftForge.EVENT_BUS.register(new DropsEvent());
//        MinecraftForge.EVENT_BUS.register(new CloneEvent());
    }
}
