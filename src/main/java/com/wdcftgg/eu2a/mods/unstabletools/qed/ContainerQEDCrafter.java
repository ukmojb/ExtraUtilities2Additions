package com.wdcftgg.eu2a.mods.unstabletools.qed;

import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityQED;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerQEDCrafter extends Container {

    private final TileEntityQED tile;

    public ContainerQEDCrafter(InventoryPlayer playerInv, TileEntityQED tile) {
        this.tile = tile;
        IItemHandler handler = tile.getInventory();

        // 输入槽 0-8
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int index = row * 3 + col;
                addSlotToContainer(new SlotItemHandler(handler, index, 31 + col * 18, 17 + row * 18));
            }
        }

        // 输出槽 9（不可放入）
        addSlotToContainer(new SlotItemHandler(handler, 9, 125, 35) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        // 玩家背包槽
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 87 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInv, col, 8 + col * 18, 145));
        }
//        addSlotToContainer(new Slot(playerInv, 0, 9, 144));
//        addSlotToContainer(new Slot(playerInv, 1, 28, 143));
//        addSlotToContainer(new Slot(playerInv, 2, 46, 143));
//        addSlotToContainer(new Slot(playerInv, 3, 64, 143));
//        addSlotToContainer(new Slot(playerInv, 4, 83, 143));
//        addSlotToContainer(new Slot(playerInv, 5, 101, 143));
//        addSlotToContainer(new Slot(playerInv, 6, 119, 143));
//        addSlotToContainer(new Slot(playerInv, 7, 138, 143));
//        addSlotToContainer(new Slot(playerInv, 8, 156, 143));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.getWorld().getTileEntity(tile.getPos()) == tile &&
                playerIn.getDistanceSq(tile.getPos().add(0.5, 0.5, 0.5)) <= 64;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        // 可按需实现 shift-click 移动物品逻辑
        return ItemStack.EMPTY;
    }
}
