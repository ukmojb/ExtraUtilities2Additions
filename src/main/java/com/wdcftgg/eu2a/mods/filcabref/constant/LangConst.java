package com.wdcftgg.eu2a.mods.filcabref.constant;

import com.wdcftgg.eu2a.mods.filcabref.FilingCabinetsMod;

public class LangConst {

    public static final String BLOCK_FILING_CABINET = "filing_cabinet";

    public static final String ITEM_MATERIAL = "material";

    public static final String GUI_FILING_CABINET = "filing_cabinet";

    private static final String GUI_NAME_KEY = FilingCabinetsMod.MOD_ID + ".container.";
    public static final String GUI_NAME_FILING_CABINET_BASIC = GUI_NAME_KEY + "filing_cabinet.basic";
    public static final String GUI_NAME_FILING_CABINET_ADVANCED = GUI_NAME_KEY + "filing_cabinet.advanced";

    private static final String MISC_KEY = FilingCabinetsMod.MOD_ID + ".misc.";

    private static final String TT_KEY = MISC_KEY + "tooltip.";
    public static final String TT_FILING_CABINET_BASIC = TT_KEY + "filing_cabinet.basic";
    public static final String TT_FILING_CABINET_ADVANCED = TT_KEY + "filing_cabinet.advanced";
    public static final String TT_CAPACITY_UPGRADE = TT_KEY + "capacity_upgrade";
    public static final String TT_CABINET_INFO_SLOT_USE = TT_KEY + "cabinet_info.slot_use";
    public static final String TT_CABINET_INFO_ITEM_USE = TT_KEY + "cabinet_info.item_use";
    public static final String TT_ITEM_COUNT = TT_KEY + "item_count";

    private static final String NOTIF_KEY = MISC_KEY + "notif.";
    public static final String NOTIF_CAP_UPGRADE_SUCCESS = NOTIF_KEY + "capacity_upgrade.success";
    public static final String NOTIF_CAP_UPGRADE_MAXED = NOTIF_KEY + "capacity_upgrade.maxed";

}
