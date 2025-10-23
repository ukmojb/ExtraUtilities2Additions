package com.wdcftgg.eu2a.network;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/13 23:29
 */

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE;
    public static int num = 0;

    public PacketHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(MessageOpenInventory.class, MessageOpenInventory.class, num++, Side.SERVER);
        INSTANCE.registerMessage(MessageRedStone.class, MessageRedStone.class, num++, Side.CLIENT);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ExtraUtilities2Additions.MODID);
    }
}
