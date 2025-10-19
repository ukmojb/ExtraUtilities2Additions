package com.wdcftgg.eu2a.mods.unstabletools.block.te;

import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipe;
import com.wdcftgg.eu2a.mods.unstabletools.recipe.QEDCraftingRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class TileEntityQED extends TileEntity implements ITickable {

    private final ItemStackHandler inventory = new ItemStackHandler(10); // 0-8: 输入, 9: 输出
    private int progress = 0;
    private int maxProgress = 0;


    // 漏斗使用这个 handler（只能提取第9个槽）
    private final IItemHandler outputOnlyHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return 10;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return slot == 9 ? inventory.getStackInSlot(slot) : ItemStack.EMPTY;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return stack; // 拒绝插入
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot == 9 ? inventory.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
    };

    @Override
    public void update() {
//        if (world.isRemote) return;

        if (canCraft()) {
            progress++;
            if (progress >= maxProgress) {
                craftItem();
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    private boolean canCraft() {
        List<ItemStack> input = createArray();
        for (int i = 0; i < 9; i++) {
            input.set(i, inventory.getStackInSlot(i));
        }

        QEDCraftingRecipe match = QEDCraftingRecipeManager.findMatchingRecipe(input);
        if (match == null) return false;
        if (!inventory.getStackInSlot(9).isEmpty()) return false;

        maxProgress = match.getCraftTime();
        return true;
    }

    private void craftItem() {
        List<ItemStack> input = createArray();
        for (int i = 0; i < 9; i++) {
            input.set(i, inventory.getStackInSlot(i));
        }

        QEDCraftingRecipe match = QEDCraftingRecipeManager.findMatchingRecipe(input);
        if (match == null) return;

        // 消耗输入
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
                inventory.setStackInSlot(i, stack);
            }
        }

        // 设置输出
        inventory.setStackInSlot(9, match.getOutput().copy());
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                // 玩家操作（手动界面）：返回完整 handler
                return (T) inventory;
            } else {
                // 自动化设备：只能访问输出槽
                return (T) outputOnlyHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Items", inventory.serializeNBT());
        compound.setInteger("Progress", progress);
        compound.setInteger("MaxProgress", maxProgress);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("Items"));
        progress = compound.getInteger("Progress");
        maxProgress = compound.getInteger("MaxProgress");
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    private List<ItemStack> createArray() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(ItemStack.EMPTY);
        }
        return list;
    }
}
