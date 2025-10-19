package com.wdcftgg.eu2a.mods.unstabletools;

import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.blocks.BlockCursedEarth;
import com.wdcftgg.eu2a.ExtraUtilities2Additions;
import com.wdcftgg.eu2a.item.ModItems;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityChandelier;
import com.wdcftgg.eu2a.mods.unstabletools.block.te.TileEntityMagnumTorch;
import com.wdcftgg.eu2a.mods.unstabletools.crafting.IDivisionItem;
import com.wdcftgg.eu2a.mods.unstabletools.item.ItemDivisionSign;
import com.wdcftgg.eu2a.mods.unstabletools.item.ItemUnstableIngot;
import com.wdcftgg.eu2a.util.SpeedHostileMobSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = ExtraUtilities2Additions.MODID)
public final class UTEvent {
    public static final DamageSource DIVIDE_BY_DIAMOND = (new DamageSource("divide_by_diamond"));
    public static final DamageSource ESCAPE_DIVIDE_BY_DIAMOND = (new DamageSource("escape_divide_by_diamond"));


    @SubscribeEvent
    public static void onSacrifice(LivingDeathEvent e){
        if (!(e.getSource().getTrueSource() instanceof EntityPlayer))return;
        EntityPlayer player = (EntityPlayer)e.getSource().getTrueSource();
        EntityLivingBase sacrifice = e.getEntityLiving();
        World world = sacrifice.world;
        BlockPos pos = sacrifice.getPosition();
        if (!world.canSeeSky(pos))return;
        Block block = world.getBlockState(pos.down()).getBlock();
        if (block != Blocks.ENCHANTING_TABLE)return;
        long time = world.getWorldInfo().getWorldTime() % 24000;
        if (time <= 17500 || time > 18500)return;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                BlockPos pos1 = new BlockPos(pos.down().getX() + i, pos.down().getY(), pos.down().getZ() + j);
                if(world.getBlockState(pos1).getBlock() != Blocks.REDSTONE_WIRE)return;
            }
        }


        for (Slot slot : player.inventoryContainer.inventorySlots){
            ItemStack stack = slot.getStack();
            if (!(stack.getItem() instanceof ItemDivisionSign)) continue;
            stack.shrink(64);

            ItemStack divisionSign = ObjectHolders.divisionSign.getDefaultInstance();

            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean("activated", true);
            nbt.setInteger("d", 256);
            divisionSign.setTagCompound(nbt);
            slot.putStack(divisionSign);
        }

        putBlockCursedEarth(pos.down(), world);
        putBlockCursedEarth(pos.down().north(), world);
        putBlockCursedEarth(pos.down().south(), world);
        putBlockCursedEarth(pos.down().south().west(), world);
        putBlockCursedEarth(pos.down().south().east(), world);
        putBlockCursedEarth(pos.down().north().west(), world);
        putBlockCursedEarth(pos.down().north().east(), world);
        putBlockCursedEarth(pos.down().east(), world);
        putBlockCursedEarth(pos.down().west(), world);
        world.setBlockToAir(pos.down().north());
        world.setBlockToAir(pos.south());
        world.setBlockToAir(pos.south().west());
        world.setBlockToAir(pos.south().south());
        world.setBlockToAir(pos.east());
        world.setBlockToAir(pos.west());
        world.setBlockToAir(pos.west().south());
        world.setBlockToAir(pos.west().north());
        sacrifice.world.addWeatherEffect(new EntityLightningBolt(sacrifice.world, sacrifice.posX, sacrifice.posY, sacrifice.posZ, false));
    }
    
    private static void putBlockCursedEarth(BlockPos pos, World world){
        BlockCursedEarth earth = (BlockCursedEarth) XU2Entries.cursedEarth.value;
        IBlockState cursedEarthState = earth.getDefaultState();
        world.setBlockState(pos.down(), cursedEarthState);
    }


    @SubscribeEvent
    public static void onPseudoInversionSigil(LivingDeathEvent e){
        if (!(e.getSource().getTrueSource() instanceof EntityPlayer))return;
        EntityPlayer player = (EntityPlayer)e.getSource().getTrueSource();
        EntityLivingBase sacrifice = e.getEntityLiving();
        World world = sacrifice.world;
        BlockPos pos = sacrifice.getPosition();
        BlockPos beaconPos = findBeaconNearby(world, pos);
        if (beaconPos == null) return;
        BlockBeacon beacon = (BlockBeacon) world.getBlockState(beaconPos).getBlock();
        if (!(sacrifice instanceof EntityIronGolem))return;
        if (world.provider.getDimension() != 1)return;

        boolean pass = ItemDivisionSign.checkPseudoInversionSigil(beaconPos, world, player, false);

        if (!pass) return;
        BlockPos[] weatherPoses = new BlockPos[] {
                beaconPos,
                beaconPos.north(5),
                beaconPos.south(5),
                beaconPos.west(5),
                beaconPos.east(5),
        };

        for (BlockPos weatherPos : weatherPoses) {
            world.setBlockToAir(weatherPos);
            world.createExplosion(player, weatherPos.getX(), weatherPos.getY(), weatherPos.getZ(), 2, true);
            world.addWeatherEffect(new EntityLightningBolt(world, weatherPos.getX(), weatherPos.getY(), weatherPos.getZ(), false));
        }

        for (Slot slot : player.inventoryContainer.inventorySlots){
            ItemStack stack = slot.getStack();
            if (!(stack.getItem() instanceof IDivisionItem))continue;
            if (stack.getTagCompound() != null) {
                stack.getTagCompound().setBoolean("pseudo_inversion_sigil", true);
                stack.getTagCompound().setInteger("kill", 100);
            } else {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean("pseudo_inversion_sigil", true);
                nbt.setInteger("kill", 100);
                stack.setTagCompound(nbt);
            }
        }

        for (int i = 0; i < 30; i++) {
            world.getMinecraftServer().addScheduledTask(() -> {
                SpeedHostileMobSpawner.spawnWithRetry(world, beaconPos, 150, 100);
            });
        }
    }

    @SubscribeEvent
    public static void onKillMob(LivingDeathEvent e){
        if (!(e.getSource().getTrueSource() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)e.getSource().getTrueSource();
        EntityLivingBase mob = e.getEntityLiving();
        World world = mob.world;
        BlockPos pos = mob.getPosition();
        if (!(mob.isPotionActive(MobEffects.SPEED))) return;
        if (world.provider.getDimension() != 1) return;

        for (Slot slot : player.inventoryContainer.inventorySlots){
            ItemStack stack = slot.getStack();
            if (!(stack.getItem() instanceof ItemDivisionSign)) continue;
            if (stack.getTagCompound() != null) {
                boolean pis = stack.getTagCompound().getBoolean("pseudo_inversion_sigil");
                int kill = stack.getTagCompound().getInteger("kill");

                if (pis) {
                    if (kill > 0) {
                        kill --;
                        player.sendMessage(new TextComponentTranslation("eu2a.pseudo_inversion_sigil.kill_mob", (100 - kill)));

                        if (kill % 10 == 0) {
                            for (Entity entity : world.loadedEntityList) {
                                if (entity instanceof EntityLiving) {
                                    EntityLiving entityLiving = (EntityLiving) entity;
                                    if (entityLiving.isPotionActive(MobEffects.SPEED)) {
                                        entityLiving.setDead();
                                        entityLiving.setHealth(0);
                                    }
                                }
                            }
                            for (int i = 0; i < 30; i++) {
                                world.getMinecraftServer().addScheduledTask(() -> {
                                    SpeedHostileMobSpawner.spawnWithRetry(world, pos, 150, 100);
                                });
                            }
                        }
                    }

                    if (stack.getTagCompound() != null) {
                        stack.getTagCompound().setInteger("kill", kill);
                    }


                    if (kill <= 0) {
                        world.addWeatherEffect(new EntityLightningBolt(world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), false));

                        stack.shrink(64);

                        slot.putStack(ModItems.pseudoInversionSigil.getDefaultInstance());
                        for (int i = 0; i < 30; i++) {
                            world.getMinecraftServer().addScheduledTask(() -> {
                                SpeedHostileMobSpawner.spawnWithRetry(world, pos, 150, 100);
                            });
                        }
                    }

                }
                break;
            }
        }
    }


    @SubscribeEvent
    public static void playertick(TickEvent.PlayerTickEvent e) {

        if (e.phase == TickEvent.Phase.START) return;

        World world = e.player.world;
        Container container = e.player.openContainer;
        if (UnstableToolsConfig.allowed_container_classes.contains(container.getClass())) {
            boolean explode = false;

            for (Slot slot : container.inventorySlots) {
                ItemStack stack = slot.getStack();
                if (!(stack.getItem() instanceof ItemUnstableIngot) || !stack.hasTagCompound() || slot instanceof SlotCrafting)
                    continue;
                int timer = stack.getTagCompound().getInteger("timer");
                if (timer == 0) {
                    stack.shrink(1);
                    explode = true;
                    continue;
                }
                stack.getTagCompound().setInteger("timer", --timer);
            }
            if (!world.isRemote)
                container.detectAndSendChanges();

            if (!explode) return;
            EntityPlayer p = e.player;
            world.createExplosion(null, p.posX, p.posY, p.posZ, 1, false);
            p.attackEntityFrom(DIVIDE_BY_DIAMOND, 100);
        }
    }

    @SubscribeEvent
    public static void onContainerClose(PlayerContainerEvent.Close e) {
        EntityPlayer p = e.getEntityPlayer();
        Container c = e.getContainer();
        boolean explode = false;
        for (Slot slot : c.inventorySlots) {
            ItemStack stack = slot.getStack();
            if (!checkExplosion(stack) || slot instanceof SlotCrafting) continue;
            stack.shrink(1);
            explode = true;
        }
        if (!explode) return;
        p.world.createExplosion(null, p.posX, p.posY, p.posZ, 1, false);
        p.attackEntityFrom(ESCAPE_DIVIDE_BY_DIAMOND, 100);
    }

    @SubscribeEvent
    public static void onItemDrop(ItemTossEvent e) {
        EntityPlayer p = e.getPlayer();
        EntityItem entityItem = e.getEntityItem();
        ItemStack stack = entityItem.getItem();
        if (checkExplosion(stack)) {
            p.world.createExplosion(null, p.posX, p.posY, p.posZ, 1, false);
            p.attackEntityFrom(ESCAPE_DIVIDE_BY_DIAMOND, 100);
            e.setCanceled(true);
        }
    }

    public static boolean checkExplosion(ItemStack stack) {
        return stack.hasTagCompound() && stack.getItem() instanceof ItemUnstableIngot && stack.getTagCompound().getInteger("timer") > 0;
    }

    @Nullable
    public static BlockPos findBeaconNearby(World world, BlockPos centerPos) {
        final int range = 3;

        for (int xOffset = -range; xOffset <= range; xOffset++) {
            for (int yOffset = -range; yOffset <= range; yOffset++) {
                for (int zOffset = -range; zOffset <= range; zOffset++) {
                    BlockPos checkPos = centerPos.add(xOffset, yOffset, zOffset);

                    if (world.getBlockState(checkPos).getBlock() instanceof BlockBeacon) {
                        return checkPos;
                    }
                }
            }
        }
        return null;
    }

    @SubscribeEvent
    public static void onMobSpawnCheck(LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawner() != null || event.getResult() == Event.Result.ALLOW) {
            return;
        }

        if (!(event.getEntity() instanceof EntityMob)) return;

        World world = event.getWorld();
        BlockPos centerPos = new BlockPos(event.getX(), event.getY(), event.getZ());

        for (TileEntity tile : world.loadedTileEntityList) {
            if (tile instanceof TileEntityMagnumTorch) {
                BlockPos mobPos = event.getEntity().getPosition();
                double length = new Vec3d(mobPos.getX() - centerPos.getX(), mobPos.getY() - centerPos.getY(), mobPos.getZ() - centerPos.getZ()).length();

                if (length < 128) event.setResult(Event.Result.DENY);
            }
            if (tile instanceof TileEntityChandelier) {
                BlockPos mobPos = event.getEntity().getPosition();
                double length = new Vec3d(mobPos.getX() - centerPos.getX(), mobPos.getY() - centerPos.getY(), mobPos.getZ() - centerPos.getZ()).length();

                if (length < 16) event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onEvent(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityWither && (event.getSource().getTrueSource() instanceof EntityPlayer) &&!(event.getSource().getTrueSource() instanceof FakePlayer)) {
            ItemStack itemStackToDrop = new ItemStack(ObjectHolders.divisionSign);
            event.getDrops().add(new EntityItem(entity.world, entity.posX,
                    entity.posY, entity.posZ, itemStackToDrop));
        }
    }

}
