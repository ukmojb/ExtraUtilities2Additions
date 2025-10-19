package com.wdcftgg.eu2a.mods.unstabletools.item;

import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.mods.unstabletools.UnstableTools;
import com.wdcftgg.eu2a.mods.unstabletools.crafting.IDivisionItem;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mod.EventBusSubscriber
public class ItemDivisionSign extends Item implements IDivisionItem, IItemColored {

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.hasTagCompound() && (stack.getTagCompound().getBoolean("activated") || stack.getTagCompound().getBoolean("activated"));
    }

    @Override
    @Nonnull
    public ItemStack getContainerItem(ItemStack itemStack) {
      return damage(itemStack.copy());
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab)) return;

        items.add(new ItemStack(this));

        ItemStack withNBT = new ItemStack(this);
        NBTTagCompound tag = new NBTTagCompound();

        tag.setBoolean("activated", true);
        tag.setInteger("d", 256);

        withNBT.setTagCompound(tag);
        items.add(withNBT);
    }

    public static ItemStack damage(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        boolean stable = false;
        if (stack.getTagCompound() != null) {
            stable = stack.getTagCompound().getBoolean("stable");
        }
        if (stable)
          return stack;
        int d = nbt.getInteger("d");
        d--;
        if (d <= 0) {
            nbt.removeTag("d");
            nbt.removeTag("activated");
        } else {
            nbt.setInteger("d", d);
        }
        stack.setTagCompound(nbt);
        return stack;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.OFF_HAND || world.isRemote)return EnumActionResult.FAIL;

        Block block = world.getBlockState(pos).getBlock();
        if (block == Blocks.ENCHANTING_TABLE) {
            long time = world.getWorldInfo().getWorldTime() % 24000;

            boolean correctTime = false;
            if (time <= 17500)
                player.sendMessage(new TextComponentTranslation("unstabletools.early"));
            else if (time <= 18500) {
                player.sendMessage(new TextComponentTranslation("unstabletools.ontime"));
                correctTime = true;
            } else player.sendMessage(new TextComponentTranslation("unstabletools.late"));

            boolean circle = true;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) continue;
                    BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                    if (world.getBlockState(pos1).getBlock() != Blocks.REDSTONE_WIRE) circle = false;
                }
            }

            if (!circle)
                player.sendMessage(new TextComponentTranslation("unstabletools.incomplete"));

            boolean skyVisible = world.canSeeSky(pos.up());

            if (!skyVisible)
                player.sendMessage(new TextComponentTranslation("unstabletools.nosky"));

            if (correctTime && circle && skyVisible) player.sendMessage(new TextComponentTranslation("unstabletools.ready"));

        } else if (block == Blocks.BEACON){
            boolean pass = checkPseudoInversionSigil(pos, world, player, true);
            player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.all." + pass));
        }
        return EnumActionResult.PASS;
    }



    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        if (!stack.hasTagCompound()) return;
        boolean stable = stack.getTagCompound().getBoolean("stable");

        if (stable) {
            tooltip.add(I18n.format(UnstableTools.MODID + ".stable"));
            return;
        }

        boolean activated = stack.getTagCompound().getBoolean("activated");
        tooltip.add(I18n.format(UnstableTools.MODID + ".activated") + activated);
        if (!activated) return;
        tooltip.add(I18n.format(UnstableTools.MODID + ".uses_left") + stack.getTagCompound().getInteger("d"));
    }



    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return (!stack.hasTagCompound()) ? 0xee0000 : (stack.getTagCompound().getBoolean("stable")) ? 0x50dd00 : 0xee0000;
    }

    public static boolean checkPseudoInversionSigil(BlockPos pos, World world, EntityPlayer player, boolean hasTip) {

        return checkChest(pos, world, player, hasTip) &&
                            checkRedStone(pos, world, player, hasTip) &&
                                checkString(pos, world, player, hasTip);
    }

    public static boolean checkChest(BlockPos pos, World world, EntityPlayer player, boolean hasTip) {
        //north
        ItemStack[] northItems = new ItemStack[] {
                new ItemBlock(Blocks.STONE).getDefaultInstance(),
                new ItemBlock(Blocks.GLASS).getDefaultInstance(),
                new ItemBlock(Blocks.HARDENED_CLAY).getDefaultInstance(),
                new ItemStack(Items.COAL, 1, 1),
                new ItemStack(Items.DYE, 1, 2),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.IRON_INGOT),
                new ItemStack(Items.COOKED_PORKCHOP),
                new ItemStack(Items.COOKED_BEEF),
                new ItemStack(Items.COOKED_CHICKEN),
                new ItemStack(Items.BAKED_POTATO),
                new ItemStack(Items.COOKED_FISH),
        };

        BlockPos northPos = pos.north(5);
        Block northblock = world.getBlockState(northPos).getBlock();
        boolean hasNorthAllItem = true;
        if (northblock.equals(Blocks.CHEST)) {
            TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(northPos);
            if (tileEntityChest != null && !tileEntityChest.getItems().isEmpty()) {
                int listNoAir = (int) tileEntityChest.getItems().stream().filter(c -> !c.isEmpty()).count();
                for (ItemStack itemStack : northItems) {
                    if (itemStack.isEmpty()) continue;

                    if (!listHasItemStack(tileEntityChest.getItems(), itemStack)) {
                        hasNorthAllItem = false;
                    }

                }
                if (listNoAir > 12) hasNorthAllItem = false;
            }
        }

        if (hasTip) player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.chest.north." + hasNorthAllItem));

        //east
        List<String> potionList = new ArrayList<>();
        BlockPos eastPos = pos.east(5);
        Block eastblock = world.getBlockState(eastPos).getBlock();
        boolean hasEastAllItem = true;
        if (eastblock.equals(Blocks.CHEST)) {
            TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(eastPos);
            if (tileEntityChest != null && !tileEntityChest.getItems().isEmpty()) {
                int listNoAir = (int) tileEntityChest.getItems().stream().filter(c -> !c.isEmpty()).count();
                for (ItemStack itemStack : tileEntityChest.getItems()) {
                    if (itemStack.isEmpty()) continue;

                    if (!itemStack.getItem().equals(Items.POTIONITEM)) {
                        hasEastAllItem = false;
                    } else {
                        List<PotionEffect> potionEffectList = PotionUtils.getEffectsFromStack(itemStack);
                        potionList.addAll(potionEffectList.stream().map(p -> p.getPotion().getName()).collect(Collectors.toList()));
                    }

                }

                List<String> newList = potionList.stream()
                        .distinct()
                        .collect(Collectors.toList());

                if (newList.size() < 12 || listNoAir != 12) hasEastAllItem = false;
            }
        }

        if (hasTip) player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.chest.east." + hasEastAllItem));

        //west
        ItemStack[] westItems = new ItemStack[] {
                new ItemStack(Items.RECORD_13),
                new ItemStack(Items.RECORD_CAT),
                new ItemStack(Items.RECORD_BLOCKS),
                new ItemStack(Items.RECORD_CHIRP),
                new ItemStack(Items.RECORD_FAR),
                new ItemStack(Items.RECORD_MALL),
                new ItemStack(Items.RECORD_MELLOHI),
                new ItemStack(Items.RECORD_STAL),
                new ItemStack(Items.RECORD_STRAD),
                new ItemStack(Items.RECORD_WARD),
                new ItemStack(Items.RECORD_11),
                new ItemStack(Items.RECORD_WAIT),
        };
        BlockPos westPos = pos.west(5);
        Block westblock = world.getBlockState(westPos).getBlock();
        boolean hasWestAllItem = true;
        if (westblock.equals(Blocks.CHEST)) {
            TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(westPos);
            if (tileEntityChest != null && !tileEntityChest.getItems().isEmpty()) {
                int listNoAir = (int) tileEntityChest.getItems().stream().filter(c -> !c.isEmpty()).count();
                for (ItemStack itemStack : westItems) {
                    if (itemStack.isEmpty()) continue;

                    if (!listHasItemStack(tileEntityChest.getItems(), itemStack)) {
                        hasWestAllItem = false;
                    }

                }
                if (listNoAir > 12) hasWestAllItem = false;
            }
        }

        if (hasTip) player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.chest.west." + hasWestAllItem));


        //south
        ItemStack[] southItems = new ItemStack[] {
                new ItemBlock(Blocks.GRASS).getDefaultInstance(),
                new ItemBlock(Blocks.DIRT).getDefaultInstance(),
                new ItemBlock(Blocks.SAND).getDefaultInstance(),
                new ItemBlock(Blocks.GRAVEL).getDefaultInstance(),
                new ItemBlock(Blocks.CLAY).getDefaultInstance(),
                new ItemBlock(Blocks.COAL_ORE).getDefaultInstance(),
                new ItemBlock(Blocks.IRON_ORE).getDefaultInstance(),
                new ItemBlock(Blocks.GOLD_ORE).getDefaultInstance(),
                new ItemBlock(Blocks.LAPIS_ORE).getDefaultInstance(),
                new ItemBlock(Blocks.REDSTONE_ORE).getDefaultInstance(),
                new ItemBlock(Blocks.EMERALD_ORE).getDefaultInstance(),
                new ItemBlock(Blocks.DIAMOND_ORE).getDefaultInstance(),
        };
        BlockPos southPos = pos.south(5);
        Block southblock = world.getBlockState(southPos).getBlock();
        boolean hasSouthAllItem = true;
        if (southblock.equals(Blocks.CHEST)) {
            TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(southPos);
            if (tileEntityChest != null && !tileEntityChest.getItems().isEmpty()) {
                int listNoAir = (int) tileEntityChest.getItems().stream().filter(c -> !c.isEmpty()).count();
                for (ItemStack itemStack : southItems) {
                    if (itemStack.isEmpty()) continue;

                    if (!listHasItemStack(tileEntityChest.getItems(), itemStack)) {
                        hasSouthAllItem = false;
                    }

                }
                if (listNoAir > 12) hasSouthAllItem = false;
            }
        }

        if (hasTip) player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.chest.south." + hasSouthAllItem));




        return true;
    }

    private static boolean checkRedStone(BlockPos pos, World world, EntityPlayer player, boolean hasTip) {
        BlockPos[] blockPoses = new BlockPos[] {
                pos.east(),
                pos.east().north(),
                pos.east().north(2),
                pos.east().north(2).west(),
                pos.east().north(2).west(2),
                pos.east().north(2).west(3),
                pos.east().north(2).west(4),
                pos.east().north(2).west(4).south(),
                pos.east().north(2).west(4).south(2),
                pos.east().north(2).west(4).south(3),
                pos.east().north(2).west(4).south(4),
                pos.east().north(2).west(4).south(5),
                pos.east().north(2).west(4).south(6),
                pos.east().north(2).west(4).south(6).east(),
                pos.east().north(2).west(4).south(6).east(2),
                pos.east().north(2).west(4).south(6).east(3),
                pos.east().north(2).west(4).south(6).east(4),
                pos.east().north(2).west(4).south(6).east(5),
                pos.east().north(2).west(4).south(6).east(6),
                pos.east().north(2).west(4).south(6).east(7),
                pos.west(),
                pos.west().south(),
                pos.west().south(2),
                pos.west().south(2).east(),
                pos.west().south(2).east(2),
                pos.west().south(2).east(3),
                pos.west().south(2).east(4),
                pos.west().south(2).east(4).north(),
                pos.west().south(2).east(4).north(2),
                pos.west().south(2).east(4).north(3),
                pos.west().south(2).east(4).north(4),
                pos.west().south(2).east(4).north(5),
                pos.west().south(2).east(4).north(6),
                pos.west().south(2).east(4).north(6).west(),
                pos.west().south(2).east(4).north(6).west(2),
                pos.west().south(2).east(4).north(6).west(3),
                pos.west().south(2).east(4).north(6).west(4),
                pos.west().south(2).east(4).north(6).west(5),
                pos.west().south(2).east(4).north(6).west(6),
                pos.west().south(2).east(4).north(6).west(7),

        };

        boolean allRedStone = true;
        for (BlockPos stringPos : blockPoses){
            Block block = world.getBlockState(stringPos).getBlock();
            if (!block.equals(Blocks.REDSTONE_WIRE)) allRedStone = false;
        }

        if (hasTip) player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.red_stone." + allRedStone));

        return allRedStone;
    }

    private static boolean checkString(BlockPos pos, World world, EntityPlayer player, boolean hasTip) {
        BlockPos[] blockPoses = new BlockPos[] {
                pos.north(),
                pos.north().west(),
                pos.north().west(2),
                pos.north().west(2).south(),
                pos.north().west(2).south(2),
                pos.north().west(2).south(3),
                pos.north().west(2).south(4),
                pos.north().west(2).south(4).east(),
                pos.north().west(2).south(4).east(2),
                pos.north().west(2).south(4).east(3),
                pos.north().west(2).south(4).east(4),
                pos.north().west(2).south(4).east(5),
                pos.north().west(2).south(4).east(6),
                pos.north().west(2).south(4).east(6).north(),
                pos.north().west(2).south(4).east(6).north(2),
                pos.north().west(2).south(4).east(6).north(3),
                pos.north().west(2).south(4).east(6).north(4),
                pos.north().west(2).south(4).east(6).north(5),
                pos.north().west(2).south(4).east(6).north(6),
                pos.north().west(2).south(4).east(6).north(7),
                pos.south(),
                pos.south().east(),
                pos.south().east(2),
                pos.south().east(2).north(),
                pos.south().east(2).north(2),
                pos.south().east(2).north(3),
                pos.south().east(2).north(4),
                pos.south().east(2).north(4).west(),
                pos.south().east(2).north(4).west(2),
                pos.south().east(2).north(4).west(3),
                pos.south().east(2).north(4).west(4),
                pos.south().east(2).north(4).west(5),
                pos.south().east(2).north(4).west(6),
                pos.south().east(2).north(4).west(6).south(),
                pos.south().east(2).north(4).west(6).south(2),
                pos.south().east(2).north(4).west(6).south(3),
                pos.south().east(2).north(4).west(6).south(4),
                pos.south().east(2).north(4).west(6).south(5),
                pos.south().east(2).north(4).west(6).south(6),
                pos.south().east(2).north(4).west(6).south(7),
        };

        boolean allString = true;
        for (BlockPos stringPos : blockPoses){
            Block block = world.getBlockState(stringPos).getBlock();
            if (!block.equals(Blocks.TRIPWIRE)) allString = false;
        }

        if (hasTip) player.sendMessage(new TextComponentTranslation(ExtraUtilities2Additions.MODID + ".pseudo_inversion_sigil.string." + allString));

        return allString;
    }

    private static boolean listHasItemStack(NonNullList<ItemStack> list, ItemStack itemStack) {
        boolean pass = false;
        for (ItemStack itemStack1 : list) {
            if (itemStack1.isEmpty()) continue;
            if (isSameItemStack(itemStack1, itemStack)) {
                pass = true;
                break;
            }
        }

        return pass;
    }

    public static boolean isSameItemStack(ItemStack stack1, ItemStack stack2) {
        if (stack1 == stack2) return true;
        if (stack1.isEmpty() && stack2.isEmpty()) return true;
        if (stack1.isEmpty() || stack2.isEmpty()) return false;

        Item item1 = stack1.getItem();
        Item item2 = stack2.getItem();


        if (item1 == item2) {
            return stack1.getMetadata() == stack2.getMetadata();
        }


        Block block1 = item1 instanceof ItemBlock ? ((ItemBlock) item1).getBlock() : Block.getBlockFromItem(item1);
        Block block2 = item2 instanceof ItemBlock ? ((ItemBlock) item2).getBlock() : Block.getBlockFromItem(item2);

        if (block1 == Blocks.AIR || block2 == Blocks.AIR) {
            return false;
        }

        return block1 == block2 && stack1.getMetadata() == stack2.getMetadata();
    }
}
