package com.wdcftgg.eu2a;


import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.crafting.CraftingHelper;
import com.wdcftgg.eu2a.item.ItemBlockBedrockiumBlock;
import com.wdcftgg.eu2a.item.ModItems;
import com.wdcftgg.eu2a.mods.unstabletools.ObjectHolders;
import com.wdcftgg.eu2a.mods.unstabletools.UnstableToolsConfig;
import com.wdcftgg.eu2a.mods.unstabletools.block.*;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityChandelier;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityConveyorBelt;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityMagnumTorch;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityQED;
import com.wdcftgg.eu2a.mods.unstabletools.item.ItemDivisionSign;
import com.wdcftgg.eu2a.mods.unstabletools.item.ItemUnstableIngot;
import com.wdcftgg.eu2a.mods.unstabletools.qed.ModGuiHandler;
import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipeManager;
import com.wdcftgg.eu2a.mods.unstabletools.tools.*;
import com.wdcftgg.eu2a.network.PacketHandler;
import com.wdcftgg.eu2a.proxy.CommonProxy;
import com.wdcftgg.eu2a.proxy.ServerProxy;
import com.wdcftgg.eu2a.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;

@Mod.EventBusSubscriber
@Mod(modid = ExtraUtilities2Additions.MODID, name = ExtraUtilities2Additions.NAME, version = ExtraUtilities2Additions.VERSION, dependencies="required-after:extrautils2")
public class ExtraUtilities2Additions {
    public static final String MODID = "eu2a";
    public static final String NAME = "ExtraUtilities2Additions";
    public static final String VERSION = "2.1";

    public static final String CLIENT_PROXY_CLASS = "com.wdcftgg.eu2a.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.wdcftgg.eu2a.proxy.CommonProxy";

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static ServerProxy serverProxy;
    public static Logger logger;


    @Mod.Instance
    public static ExtraUtilities2Additions instance;




    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.onPreInit();
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void onAvailable(FMLLoadCompleteEvent evt)
    {
//        RecipeSorter.register(MODID + ":soulfragment_craft",  RecipeSoulFragment.class,  SHAPELESS, "after:minecraft:shapeless");

    }


    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {

        proxy.onInit();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new ModGuiHandler());

        QEDCraftingRecipeManager.addRecipe(20, new ItemStack(ObjectHolders.magnumTorch), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.REGENERATION), new ItemStack(ObjectHolders.chandelier), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING), new ItemStack(ObjectHolders.chandelier), new ItemStack(Blocks.LOG), new ItemStack(ObjectHolders.chandelier), new ItemStack(ObjectHolders.chandelier), new ItemStack(Blocks.LOG), new ItemStack(ObjectHolders.chandelier));

//        CraftingHelper.addShaped("unstable_nugget", XU2Entries.unstableIngots.newStack(1, 1), new Object[]{"i", "d", "s", 'i', "nuggetIron", 'd', "stickWood", 's', "gemDiamond"});
        GameRegistry.addSmelting(Blocks.QUARTZ_BLOCK, new ItemStack(ObjectHolders.blockBurntQuartz), 5);
        GameRegistry.addSmelting(new ItemStack(XU2Entries.compressedCobblestone.value, 1, 7), new ItemStack(ObjectHolders.bedrockiumBlock), 5);


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

        IForgeRegistry<Item> registry = event.getRegistry();

        registerItem(new ItemUnstableIngot(), "unstable_ingot", registry);
        registerItem(new ItemUnstableShears(), "unstable_shears", registry);

//        registerItemBlock(ObjectHolders.unstableBlock, registry);

        registerItem(new ItemUnstableAxe(UNSTABLE), "unstable_axe", registry);
        registerItem(new ItemUnstableSpade(UNSTABLE), "unstable_spade", registry);
        registerItem(new ItemUnstablePickaxe(UNSTABLE), "unstable_pickaxe", registry);
        registerItem(new ItemUnstableSword(UNSTABLE), "unstable_sword", registry);
        registerItem(new ItemUnstableHoe(UNSTABLE), "unstable_hoe", registry);
//        registerItem(new ItemBlock(Block.getBlockFromName("unstable_block")), "unstable_block", registry);


        for (Block block : MOD_BLOCKS) {
            if (block instanceof BlockBedrockiumBlock) {
                registerItem(new ItemBlockBedrockiumBlock(block), block.getRegistryName().getPath(), registry);
            } else {
                registerItem(new ItemBlock(block), block.getRegistryName().getPath(), registry);
            }
        }
        ItemDivisionSign divisionSign = new ItemDivisionSign();
        divisionSign.setMaxStackSize(1);
        registerItem(divisionSign, "division_sign", registry);

//        STItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
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


        for (Item item : MOD_ITEMS)
            registerModelLocation(item);
        for (Block block : MOD_BLOCKS)
            registerModelLocation(Item.getItemFromBlock(block));
    }


    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
    }



    public static final Item.ToolMaterial UNSTABLE = EnumHelper.addToolMaterial("UNSTABLE", 4, Short.MAX_VALUE, 8, 4, 25);
    public static final ItemArmor.ArmorMaterial UNSTABLE_ARMOR = EnumHelper.addArmorMaterial(MODID + ":unstable_armor", MODID + ":unstable", Short.MAX_VALUE, new int[]{4, 7, 9, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 5);
    public static final List<Item> MOD_ITEMS = new ArrayList<>();
    public static final List<Block> MOD_BLOCKS = new ArrayList<>();


    public static CreativeTabs creativeTab = new CreativeTabs(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.soulFragment);
        }
    };

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        UNSTABLE.setRepairItem(new ItemStack(ObjectHolders.unstableIngot));
        OreDictionary.registerOre("ingotUnstable", ObjectHolders.unstableIngot);
        OreDictionary.registerOre("ingotUnstable", ModItems.MobiusIngot);
        OreDictionary.registerOre("blockUnstable", ObjectHolders.unstableBlock);
        UnstableToolsConfig.sync();

        GameRegistry.registerTileEntity(TileEntityMagnumTorch.class, new ResourceLocation(MODID, "TileEntityMagnumTorch"));
        GameRegistry.registerTileEntity(TileEntityChandelier.class, new ResourceLocation(MODID, "TileEntityChandelier"));
        GameRegistry.registerTileEntity(TileEntityConveyorBelt.class, new ResourceLocation(MODID, "TileEntityConveyorBelt"));
        GameRegistry.registerTileEntity(TileEntityQED.class, new ResourceLocation(MODID, "TileEntityQED"));

    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registerBlock(new BlockQED(Material.IRON), "qed_block", registry, 1500, 10);
        registerBlock(new BlockEnderInfusedObsidian(Material.IRON), "ender_infused_obsidian_block", registry, 1500, 50);
        registerBlock(new BlockUnstableBlock(Material.IRON), "unstable_block", registry, 3000, 5);
        registerBlock(new BlockBedrockiumBlock(Material.IRON), "bedrockium_block", registry, 3000, 50);
        registerBlock(new BlockBurntQuartz(Material.IRON), "burnt_quartz_block", registry, 3000, 5);
        registerBlock(new BlockDiamondEtchedComputationalMatrix(Material.IRON), "diamond_etched_computational_matrix_block", registry, 1000, 5);
        registerBlock(new BlockChandelier(Material.GLASS), "chandelier_block", registry, 10, 2);
        registerBlock(new BlockConveyorBelt(Material.IRON), "conveyor_belt_block", registry, 10, 2);
        registerBlock(new BlockMagnumTorch(Material.WOOD), "magnumtorch_block", registry, 300, 5);
    }

    private static void registerBlock(Block block, String name, IForgeRegistry<Block> registry, float blastResistance, float hardness) {
        block.setRegistryName(name);
        block.setTranslationKey(block.getRegistryName().toString());
        block.setCreativeTab(creativeTab);
        block.setResistance(blastResistance);
        block.setHardness(hardness);
        MOD_BLOCKS.add(block);
        registry.register(block);
    }

    private static void registerItem(Item item, String name, IForgeRegistry<Item> registry) {
        item.setRegistryName(name);
        item.setTranslationKey(item.getRegistryName().toString());
        item.setCreativeTab(creativeTab);
        MOD_ITEMS.add(item);
        registry.register(item);
    }

    private static void registerItemBlock(Block block, IForgeRegistry<Item> registry) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        registry.register(itemBlock);
    }

    private static void registerModelLocation(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }


}
