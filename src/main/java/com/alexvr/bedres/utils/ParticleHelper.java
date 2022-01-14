package com.alexvr.bedres.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ParticleHelper {

    public static void spawnItemParticlesWithVelocity(BlockPos blockPos, ItemStack stack, double pXVel, double pYVel, double pZVel,double pXOffset, double pYOffset, double pZOffset, Level level) {
        if (level == null || level.isClientSide() || stack.isEmpty())
            return;

        var serverLevel = (ServerLevel) level;

        double x = blockPos.getX() + (serverLevel.getRandom().nextDouble() * 0.2D) + pXOffset;
        double y = blockPos.getY() + (serverLevel.getRandom().nextDouble() * 0.2D) + pYOffset;
        double z = blockPos.getZ() + (serverLevel.getRandom().nextDouble() * 0.2D) + pZOffset;

        serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y, z, 0, pXVel, pYVel, pZVel, 0.10D);
    }

    public static void spawnItemParticles(BlockPos start, ItemStack stack, Level level, BlockPos endPos, double ySpeedAndAngle,double pXOffset, double pYOffset, double pZOffset) {
        if (level == null || level.isClientSide() || stack.isEmpty())
            return;

        var serverLevellevel = (ServerLevel) level;
        var pos = endPos;

        double x = start.getX() + (serverLevellevel.getRandom().nextDouble() * 0.2D) + pXOffset;
        double y = start.getY() + (serverLevellevel.getRandom().nextDouble() * 0.2D) + pYOffset;
        double z = start.getZ() + (serverLevellevel.getRandom().nextDouble() * 0.2D) + pZOffset;

        double velX = pos.getX() - start.getX();
        double velY = ySpeedAndAngle;
        double velZ = pos.getZ() - start.getZ();

        serverLevellevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y, z, 0, velX, velY, velZ, 0.10D);
    }
}
