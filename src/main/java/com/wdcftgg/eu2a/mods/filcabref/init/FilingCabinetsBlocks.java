package com.wdcftgg.eu2a.mods.filcabref.init;

import com.wdcftgg.eu2a.mods.filcabref.FilingCabinetsMod;
import com.wdcftgg.eu2a.mods.filcabref.block.filing_cabinet.BlockFilingCabinet;
import com.wdcftgg.eu2a.mods.filcabref.constant.LangConst;
import io.github.phantamanta44.libnine.InitMe;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("NotNullFieldNotInitialized")
public class FilingCabinetsBlocks {

    @GameRegistry.ObjectHolder(FilingCabinetsMod.MOD_ID + ":" + LangConst.BLOCK_FILING_CABINET)
    public static BlockFilingCabinet FILING_CABINET;

    @InitMe(FilingCabinetsMod.MOD_ID)
    public static void init() {
        new BlockFilingCabinet();
    }

}
