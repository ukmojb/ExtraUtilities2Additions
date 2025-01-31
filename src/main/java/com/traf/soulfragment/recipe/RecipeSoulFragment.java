package com.traf.soulfragment.recipe;

import com.rwtema.extrautils2.backend.ISidedFunction;
import com.rwtema.extrautils2.gui.backend.WidgetCraftingMatrix;
import com.traf.soulfragment.SoulFragmentMod;
import com.traf.soulfragment.event.ClientEvent;
import com.traf.soulfragment.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeSoulFragment extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {



    public boolean matches(InventoryCrafting inv, World worldIn) {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = inv.getStackInSlot(j);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem().getRegistryName().toString().equals("unstabletools:unstable_sword"))
                {
                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.AIR)
                    {
                        return false;
                    }
                }
            }
        }
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            if (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory)) {
                return false;
            }
        }

        return !itemstack.isEmpty();
    }


    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j)
        {
            ItemStack itemstack1 = inv.getStackInSlot(j);

            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem().getRegistryName().toString().equals("unstabletools:unstable_sword"))
                {
                    if (!itemstack.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.AIR)
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (!isGoodForCrafting(inv) || !ClientEvent.oponIn) {
            return ItemStack.EMPTY;
        }



        return new ItemStack(ModItems.soulFragment);
    }



    public boolean canFit(int width, int height) {
        return width <= 2 && height <= 2;
    }


    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }


    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack.getItem().getRegistryName().toString().equals("unstabletools:unstable_sword"))
            {
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                nonnulllist.set(i, itemstack1);
                break;
            }
        }

        return nonnulllist;
    }

    public boolean isGoodForCrafting(final InventoryCrafting inv) {
        if (inv instanceof WidgetCraftingMatrix.XUInventoryCrafting) {
            EntityPlayer player = ((WidgetCraftingMatrix.XUInventoryCrafting) inv).player;
            if (player == null) return false;

            if (player instanceof EntityPlayerMP) {
                updatePlayer((EntityPlayerMP) player);
            }
            return isValidForCrafting(player);
        }

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return SoulFragmentMod.proxy.apply(new ClientIsGoodForCrafting(inv), null);
        } else {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                PlayerList manager = server.getPlayerList();
                if (manager != null) {
                    Container container = inv.eventHandler;
                    if (container == null) return false;

                    EntityPlayerMP foundPlayer = null;

                    for (EntityPlayerMP entityPlayerMP : manager.getPlayers()) {
                        if (entityPlayerMP.openContainer == container && container.canInteractWith(entityPlayerMP) && container.getCanCraft(entityPlayerMP)) {
                            if (foundPlayer != null) return false;
                            foundPlayer = entityPlayerMP;
                        }
                    }

                    if (foundPlayer != null) {
                        updatePlayer(foundPlayer);
                        return isValidForCrafting(foundPlayer);
                    }
                }
            }
            return false;
        }
    }

    protected void updatePlayer(EntityPlayerMP foundPlayer) {
        foundPlayer.connection.sendPacket(new SPacketUpdateHealth(foundPlayer.getHealth(), foundPlayer.getFoodStats().getFoodLevel(), foundPlayer.getFoodStats().getSaturationLevel()));
    }


    protected boolean isValidForCrafting(EntityPlayer foundPlayer) {
        if (foundPlayer.getMaxHealth() > 6) {
            return true;
        }
        if (!foundPlayer.world.isRemote && ClientEvent.oponIn) {
            foundPlayer.sendMessage(new TextComponentTranslation("soulfragment.soul_fragment.message"));
        }
        return false;
    }

    private class ClientIsGoodForCrafting implements ISidedFunction<Void, Boolean> {
        private final InventoryCrafting inv;

        public ClientIsGoodForCrafting(InventoryCrafting inv) {
            this.inv = inv;
        }

        @Override
        @SideOnly(Side.SERVER)
        public Boolean applyServer(Void input) {
            return false;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Boolean applyClient(Void input) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            return player != null && isValidForCrafting(player) && player.openContainer == inv.eventHandler;
        }
    }
}
