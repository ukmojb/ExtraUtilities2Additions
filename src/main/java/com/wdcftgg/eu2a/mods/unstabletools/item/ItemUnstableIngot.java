package com.wdcftgg.eu2a.mods.unstabletools.item;

import com.wdcftgg.eu2a.mods.unstabletools.UnstableTools;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemUnstableIngot extends Item implements IItemColored {

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (worldIn == null) return;
    if (!stack.hasTagCompound()) {
      tooltip.add(I18n.format(UnstableTools.MODID + ".stable"));
      return;
    }
    int timer = stack.getTagCompound().getInteger("timer");
    tooltip.add(I18n.format(UnstableTools.MODID + ".time_left") + timer);
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return (stack.hasTagCompound()) ? 1 : 64;
  }



  @Override
  public int getColor(ItemStack stack, int tintIndex) {
    if (!stack.hasTagCompound()) {
      return 0xffffff;
    } else {
      NBTTagCompound nbt = stack.getTagCompound();
      int color = nbt.getInteger("timer");
      double scale = color / 200d;

      int red, green, blue;

      if (scale >= .5) {
        red = green = 0xff;

        blue = (int) ((2 * scale - 1) * 0xff);
      } else if (scale >= .25) {
        red = 0xff;
        green = (int) (2 * scale * 0xff);
        blue = 0;
      } else {
        scale *= 256;
        scale = Math.floor(scale);
        scale %= 2;
        switch ((int) scale) {
          case 0: {
            red = 0xff;
            green = blue = 0;
            break;
          }
          case 1: {
            red = green = 0xff;
            blue = 0;
            break;
          }
          default:
            throw new IllegalStateException();
        }
      }
      return (red << 16) + (green << 8) + blue;
    }
  }
}