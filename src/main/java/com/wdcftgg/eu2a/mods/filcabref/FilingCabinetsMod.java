package com.wdcftgg.eu2a.mods.filcabref;

import io.github.phantamanta44.libnine.Virtue;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = FilingCabinetsMod.MOD_ID, version = FilingCabinetsMod.VERSION, useMetadata = true)
public class FilingCabinetsMod extends Virtue {

    public static final String MOD_ID = "filcabref";
    public static final String VERSION = "1.0.0";

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Mod.Instance(MOD_ID)
    public static FilingCabinetsMod INSTANCE;

    public FilingCabinetsMod() {
        super(MOD_ID);
    }
}
