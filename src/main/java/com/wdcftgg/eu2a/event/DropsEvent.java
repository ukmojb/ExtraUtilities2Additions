package com.wdcftgg.eu2a.event;

import com.rwtema.extrautils2.items.ItemLawSword;
import com.wdcftgg.eu2a.item.ItemSoulFragment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber
public class DropsEvent {

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {

        EntityLivingBase livingBase = event.getEntityLiving();
        Random random = new Random();

        if (event.getSource().getTrueSource() != null) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
                ItemLawSword lawSword = new ItemLawSword();
                if (player.getHeldItemMainhand().getItem().equals(lawSword)) {
                    if (random.nextInt(4304672) < 1) {
                        ItemSoulFragment soulFragment = new ItemSoulFragment();
                        EntityItem entityItem = new EntityItem(livingBase.world,livingBase.posX, livingBase.posY, livingBase.posZ, new ItemStack(soulFragment));
                        event.getDrops().add(entityItem);
                    }
                }
            }
        }

        if (random.nextInt(43046720) < 1) {
            ItemSoulFragment soulFragment = new ItemSoulFragment();
            EntityItem entityItem = new EntityItem(livingBase.world,livingBase.posX, livingBase.posY, livingBase.posZ, new ItemStack(soulFragment));
            event.getDrops().add(entityItem);
        }
    }
}
