package com.wdcftgg.eu2a.mods.unstabletools;

import com.wdcftgg.eu2a.mods.unstabletools.block.*;
import com.wdcftgg.eu2a.mods.unstabletools.item.ItemDivisionSign;
import com.wdcftgg.eu2a.mods.unstabletools.item.ItemUnstableIngot;
import com.wdcftgg.eu2a.mods.unstabletools.tools.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ObjectHolders {

  @GameRegistry.ObjectHolder("eu2a:unstable_ingot")
  public static ItemUnstableIngot unstableIngot;

  @GameRegistry.ObjectHolder("eu2a:unstable_block")
  public static BlockUnstableBlock unstableBlock;

  @GameRegistry.ObjectHolder("eu2a:bedrockium_block")
  public static BlockBedrockiumBlock bedrockiumBlock;
  @GameRegistry.ObjectHolder("eu2a:burnt_quartz_block")
  public static BlockBurntQuartz blockBurntQuartz;
  @GameRegistry.ObjectHolder("eu2a:qed_block")
  public static BlockQED qed;
  @GameRegistry.ObjectHolder("eu2a:chandelier_block")
  public static BlockChandelier chandelier;
  @GameRegistry.ObjectHolder("eu2a:magnumtorch_block")
  public static BlockMagnumTorch magnumTorch;

  @GameRegistry.ObjectHolder("eu2a:division_sign")
  public static ItemDivisionSign divisionSign;

  @GameRegistry.ObjectHolder("eu2a:unstable_axe")
  public static ItemUnstableAxe unstableAxe;

  @GameRegistry.ObjectHolder("eu2a:unstable_pickaxe")
  public static ItemUnstablePickaxe unstablePickaxe;

  @GameRegistry.ObjectHolder("eu2a:unstable_spade")
  public static ItemUnstableSpade unstableSpade;

  @GameRegistry.ObjectHolder("eu2a:unstable_sword")
  public static ItemUnstableSword unstableSword;

  @GameRegistry.ObjectHolder("eu2a:unstable_hoe")
  public static ItemUnstableHoe unstableHoe;

  @GameRegistry.ObjectHolder("eu2a:unstable_shears")
  public static ItemUnstableShears unstableShears;

}
