package com.wdcftgg.eu2a.mods.filcabref.init;

import com.wdcftgg.eu2a.mods.filcabref.FilingCabinetsMod;
import com.wdcftgg.eu2a.mods.filcabref.client.gui.GuiFilingCabinet;
import com.wdcftgg.eu2a.mods.filcabref.constant.LangConst;
import com.wdcftgg.eu2a.mods.filcabref.inventory.ContainerFilingCabinet;
import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class FilingCabinetsGuis {

    public static final GuiIdentity<ContainerFilingCabinet, GuiFilingCabinet> FILING_CABINET
            = new GuiIdentity<>(LangConst.GUI_FILING_CABINET, ContainerFilingCabinet.class);

    @InitMe
    public static void init() {
        FilingCabinetsMod.INSTANCE.getGuiHandler().registerServerGui(
                FILING_CABINET, (p, w, x, y, z) -> new ContainerFilingCabinet(p.inventory, getTileUnchecked(w, x, y, z)));
    }

    @SideOnly(Side.CLIENT)
    @InitMe(sides = { Side.CLIENT })
    public static void initClient() {
        FilingCabinetsMod.INSTANCE.getGuiHandler().registerClientGui(
                FILING_CABINET, (c, p, w, x, y, z) -> new GuiFilingCabinet(c));
    }

    @SuppressWarnings("unchecked")
    private static <T extends TileEntity> T getTileUnchecked(IBlockAccess world, int x, int y, int z) {
        return (T)Objects.requireNonNull(world.getTileEntity(new BlockPos(x, y, z)));
    }

}
