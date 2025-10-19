package com.wdcftgg.eu2a.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class Tools {

    public static void saveHealthNBT(EntityPlayer player, double value, UUID uuid) {
        NBTTagCompound nbt = new NBTTagCompound();
        player.writeToNBT(nbt);

        nbt.getCompoundTag("ForgeData").getCompoundTag("PlayerPersisted").setDouble(uuid.toString(), value);

        player.readFromNBT(nbt);
    }

    public static double getHealthNBT(EntityPlayer player, UUID uuid) {
        NBTTagCompound nbt = new NBTTagCompound();
        player.writeToNBT(nbt);

        System.out.println(nbt.toString());

        if (nbt.getCompoundTag("ForgeData").getCompoundTag("PlayerPersisted").hasKey(uuid.toString())) {
            return nbt.getCompoundTag("ForgeData").getCompoundTag("PlayerPersisted").getDouble(uuid.toString());
        }

        return -114514;
    }


}
