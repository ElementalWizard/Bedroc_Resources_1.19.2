package com.alexvr.bedres.blocks.eventAltar.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WorldEvent implements IEvent{

    public static void rain(Level level, BlockPos pos,ItemStack output) {
        if (output.is(Items.WATER_BUCKET)){
            level.getLevelData().setRaining(true);
            level.setRainLevel(0F);
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
            lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
            lightningbolt.setVisualOnly(true);
            level.addFreshEntity(lightningbolt);
        }else if (output.is(Items.BUCKET)){
            level.getLevelData().setRaining(false);
            level.setRainLevel(1.0F);
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
            lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
            lightningbolt.setVisualOnly(true);
            level.addFreshEntity(lightningbolt);
        }
    }

    public static void timeSkip(Level level, BlockPos pos,ItemStack output) {
        if (level instanceof ServerLevel serverLevel)
        if (output.is(Items.SUNFLOWER)){
            serverLevel.setDayTime(1000);
        }else if (output.is(Items.CLOCK)){
            serverLevel.setDayTime(13000);
        }
    }


    @Override
    public String getName() {
        return "world";
    }

    public static String getDescriptions(ItemStack stack) {
        if (stack.is(Items.WATER_BUCKET)){
            return "Starts Rain";
        }else if (stack.is(Items.BUCKET)){
            return "Stops Rain";
        }else if (stack.is(Items.SUNFLOWER)){
            return "Makes it daytime";
        }else if (stack.is(Items.CLOCK)){
            return "Makes it night";
        }else{
            return "No description";
        }
    }
}
