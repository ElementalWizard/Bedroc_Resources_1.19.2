package com.alexvr.bedres.blocks.eventAltar.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RainEvent implements IEvent{

    public static void start(Level level, BlockPos pos) {
        level.getLevelData().setRaining(true);
        level.setRainLevel(0F);
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
        lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
        lightningbolt.setVisualOnly(true);
        level.addFreshEntity(lightningbolt);
    }


    public static void stop(Level level, BlockPos pos) {
        level.getLevelData().setRaining(false);
        level.setRainLevel(1.0F);
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
        lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
        lightningbolt.setVisualOnly(true);
        level.addFreshEntity(lightningbolt);
    }

    @Override
    public String getName() {
        return "rain";
    }

    public static String getDescriptions(ItemStack stack) {
        if (stack.is(Items.WATER_BUCKET)){
            return "Starts Rain";
        }else{
            return "Stops Rain";
        }
    }
}
