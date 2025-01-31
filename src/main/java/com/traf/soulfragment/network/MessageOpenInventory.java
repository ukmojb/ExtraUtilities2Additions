package com.traf.soulfragment.network;


import com.traf.soulfragment.event.ClientEvent;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/8/4 8:46
 */
public class MessageOpenInventory implements IMessageHandler<MessageOpenInventory, IMessage>, IMessage  {
    public boolean opened;

    public MessageOpenInventory() {
    }

    public MessageOpenInventory(boolean opened) {
        this.opened = opened;
    }

    public void fromBytes(ByteBuf buf) {
        this.opened = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.opened);
    }


    public IMessage onMessage(MessageOpenInventory message, MessageContext ctx) {
        ClientEvent.oponIn = message.opened;
        return null;
    }
}

