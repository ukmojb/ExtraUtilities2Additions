package com.wdcftgg.eu2a.network;

import com.wdcftgg.eu2a.event.ClientEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static net.minecraft.block.BlockRedstoneWire.POWER;

public class MessageRedStone implements IMessageHandler<MessageRedStone, IMessage>, IMessage  {
    public int x;
    public int y;
    public int z;

    public MessageRedStone() {
    }

    public MessageRedStone(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }


    public IMessage onMessage(MessageRedStone message, MessageContext ctx) {
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        World world = Minecraft.getMinecraft().world;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                BlockPos pos1 = new BlockPos(pos.down().getX() + i, pos.down().getY(), pos.down().getZ() + j);
                if(world.getBlockState(pos1).getBlock() != Blocks.REDSTONE_WIRE) return null;

                IBlockState blockState = world.getBlockState(pos1);
                blockState = blockState.withProperty(POWER, 15);

                world.setBlockState(pos1, blockState, 3);
            }
        }
        return null;
    }
}

