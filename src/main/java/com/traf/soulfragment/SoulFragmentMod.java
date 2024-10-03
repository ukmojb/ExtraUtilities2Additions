package com.traf.soulfragment;


import com.traf.soulfragment.item.ModItems;
import com.traf.soulfragment.network.PacketHandler;
import com.traf.soulfragment.proxy.CommonProxy;
import com.traf.soulfragment.proxy.ServerProxy;
import com.traf.soulfragment.recipe.CraftingLoader;
import com.traf.soulfragment.recipe.RecipeSoulFragment;
import com.traf.soulfragment.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.registries.IForgeRegistry;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;

@Mod.EventBusSubscriber
@Mod(modid = SoulFragmentMod.MODID, name = SoulFragmentMod.NAME, version = SoulFragmentMod.VERSION, dependencies="required-after:extrautils2;required-after:unstabletools")
public class SoulFragmentMod {
    public static final String MODID = "soulfragment";
    public static final String NAME = "Soul Fragment";
    public static final String VERSION = "1.4";

    public static final String CLIENT_PROXY_CLASS = "com.traf.soulfragment.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.traf.soulfragment.proxy.CommonProxy";

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static ServerProxy serverProxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.onPreInit();
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void onAvailable(FMLLoadCompleteEvent evt)
    {
        RecipeSorter.register(MODID + ":soulfragment_craft",  RecipeSoulFragment.class,  SHAPELESS, "after:minecraft:shapeless");

    }


    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
//        CraftingLoader.init();

        proxy.onInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.onPostInit();

    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        for (Item item : ModItems.ITEMS.toArray(new Item[0])){
            item.setTranslationKey(MODID + "." + item.getTranslationKey().replace("item.", ""));
        }
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Item item : ModItems.ITEMS)
        {
            if (item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }

    }


    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        CraftingLoader.init(registry);
    }


}
