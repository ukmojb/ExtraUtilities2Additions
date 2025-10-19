package com.wdcftgg.eu2a.util;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class SpeedHostileMobSpawner {

    // 原版敌对生物列表（不包括boss）
    private static final Class<? extends EntityLiving>[] HOSTILE_MOBS = new Class[]{
            EntityZombie.class,          // 僵尸
            EntitySkeleton.class,        // 骷髅
            EntitySpider.class,          // 蜘蛛
            EntityCreeper.class,         // 苦力怕
            EntityEnderman.class,        // 末影人
            EntityWitch.class,           // 女巫
            EntityHusk.class,            // 尸壳
            EntityStray.class,           // 流浪者
            EntityPigZombie.class        // 僵尸猪人
    };

    /**
     * 带重试机制的生物生成方法
     * @param world 世界对象
     * @param centerPos 中心坐标
     * @param radius 生成半径
     * @param maxAttempts 最大尝试次数
     * @return 是否生成成功
     */
    public static boolean spawnWithRetry(World world, BlockPos centerPos, int radius, int maxAttempts) {
        if (world.isRemote) return false;

        Random rand = world.rand;
        int attempts = 0;

        while (attempts < maxAttempts) {
            attempts++;

            // 1. 生成随机位置
            BlockPos spawnPos = getRandomPosition(world, centerPos, radius, rand);
            if (spawnPos == null) continue; // 位置无效则重试

            double length = new Vec3d(spawnPos.getX() - centerPos.getX(), spawnPos.getY() - centerPos.getY(), spawnPos.getZ() - centerPos.getZ()).length();
            if (length < 10) continue; // 位置无效则重试

            // 2. 创建随机生物
            EntityLiving mob = createRandomMob(world, rand);
            if (mob == null) continue; // 生物创建失败则重试

            // 3. 设置位置和效果
            mob.setLocationAndAngles(
                    spawnPos.getX() + 0.5,
                    spawnPos.getY() + 0.1,
                    spawnPos.getZ() + 0.5,
                    rand.nextFloat() * 360.0F,
                    0.0F
            );

            // 4. 添加速度效果
            mob.addPotionEffect(new PotionEffect(
                    MobEffects.SPEED,
                    20 * 60 * 20, // 20分钟
                    0,            // 速度1
                    true,
                    true
            ));

            // 5. 尝试生成
            if (world.spawnEntity(mob)) {
                // 生成成功时播放音效
                world.playSound(null, spawnPos, SoundEvents.ENTITY_ENDERMEN_TELEPORT,
                        SoundCategory.HOSTILE, 0.5F, 1.0F);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取带验证的随机位置（自动重试）
     */
    private static BlockPos getRandomPosition(World world, BlockPos center, int radius, Random rand) {
        for (int i = 0; i < 5; i++) { // 每个位置尝试5次Y坐标验证
            // 圆形分布随机
            double angle = rand.nextDouble() * Math.PI * 2;
            double distance = rand.nextDouble() * radius;
            int x = center.getX() + (int)(Math.cos(angle) * distance);
            int z = center.getZ() + (int)(Math.sin(angle) * distance);

            // 获取有效Y坐标
            BlockPos pos = findValidSpawnPos(world, x, z);
            if (pos != null) return pos;
        }
        return null;
    }

    /**
     * 寻找有效生成位置（带空间检查）
     */
    private static BlockPos findValidSpawnPos(World world, int x, int z) {
        int topY = world.getHeight(x, z);

        // 从世界高度向下搜索
        for (int y = topY; y > 0; y--) {
            BlockPos pos = new BlockPos(x, y, z);

            // 检查脚下是否可站立
            if (!world.getBlockState(pos.down()).isTopSolid()) continue;

            // 检查空间是否足够（2格高）
            if (world.getBlockState(pos).getMaterial().blocksMovement() ||
                    !world.isAirBlock(pos.up())) continue;

            return pos;
        }
        return null;
    }

    /**
     * 创建随机敌对生物（带异常处理）
     */
    private static EntityLiving createRandomMob(World world, Random rand) {
        try {
            Class<? extends EntityLiving> mobClass = HOSTILE_MOBS[rand.nextInt(HOSTILE_MOBS.length)];
            EntityLiving mob = mobClass.getConstructor(World.class).newInstance(world);

            // 特殊生物初始化
            if (mob instanceof EntitySkeleton) {
                ((EntitySkeleton)mob).setCombatTask(); // 确保骷髅有武器
            }
            return mob;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 批量生成方法（带重试机制）
     * @param count 要生成的数量
     * @param attemptsPerMob 每个生物的最大尝试次数
     */
    public static int spawnMultiple(World world, BlockPos center, int count, int attemptsPerMob) {
        int success = 0;
        for (int i = 0; i < count; i++) {
            if (spawnWithRetry(world, center, 150, attemptsPerMob)) {
                success++;
            }
        }
        return success;
    }
}