package com.wdcftgg.eu2a.mods.filcabref.util;

public interface SlottedStorage {

    int getStoredQuantity();

    int getCapacity();

    default int getRemainingCapacity() {
        return getCapacity() - getStoredQuantity();
    }

    int getSlotsUsed();

    int getSlotCount();

}
