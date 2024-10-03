package com.traf.soulfragment.proxy;

import com.rwtema.extrautils2.backend.ISidedFunction;
import com.traf.soulfragment.event.ClientEvent;
import com.traf.soulfragment.event.CraftingEvent;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
	}


	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	public void onInit(){
		super.onInit();
		MinecraftForge.EVENT_BUS.register(new ClientEvent());
	}



	public void onPreInit() {
		super.onPreInit();

	}

	public void onPostInit() {
		super.onPostInit();


	}

	public static List<LayerRenderer<EntityLivingBase>> getLayerRenderers(RenderPlayer instance) {
		return (List)getPrivateValue(RenderLivingBase.class, instance, "layerRenderers");
	}

	private static <T> Object getPrivateValue(Class<T> clazz, T instance, String name) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, name);
		} catch (Exception var4) {
			return null;
		}
	}

	public <F, T> T apply(ISidedFunction<F, T> func, F input) {
		return func.applyClient(input);
	}

}
