package com.alexvr.bedres.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class GeneralHelper {

    public static void spawnInWorld(Level level, BlockPos pos, ItemStack remaining) {
        if (!remaining.isEmpty()) {
            ItemEntity entityitem = new ItemEntity(level, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, remaining);
            entityitem.setPickUpDelay(40);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));
            level.addFreshEntity(entityitem);
        }
    }

    public static BlockHitResult selectBlock(Player player) {
        // Used to find which block the player is looking at, and store it in NBT on the tool.
        Level world = player.level;
        BlockHitResult lookingAt = getLookingAt(player, ClipContext.Fluid.NONE);
        if (world.getBlockState(getLookingAt(player).getBlockPos()) == Blocks.AIR.defaultBlockState()) return null;
        return lookingAt;
    }
    public static BlockHitResult getLookingAt(Player player) {
        return getLookingAt(player,ClipContext.Fluid.NONE);
    }

    public static BlockHitResult getLookingAt(Player player, ClipContext.Fluid rayTraceFluid) {
        double rayTraceRange = 16;
        HitResult result = player.pick(rayTraceRange, 0f, rayTraceFluid != ClipContext.Fluid.NONE);

        return (BlockHitResult) result;
    }
}
