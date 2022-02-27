package com.alexvr.bedres.entities.chainedblaze;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ChainedBlazeEntity extends Blaze {


    public ChainedBlazeEntity(EntityType<? extends ChainedBlazeEntity> p_i50206_1_, Level p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide() && this.level.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove(RemovalReason.DISCARDED);
        }
        //this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);

    }
    public static AttributeSupplier.Builder prepareAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.FOLLOW_RANGE, 15.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ARMOR_TOUGHNESS,1.0D);
    }
    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 1.5f;
    }


}
