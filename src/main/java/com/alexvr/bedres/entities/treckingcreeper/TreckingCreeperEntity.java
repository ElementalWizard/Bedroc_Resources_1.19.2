package com.alexvr.bedres.entities.treckingcreeper;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.Random;

public class TreckingCreeperEntity extends Creeper {

    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.INT);

    public TreckingCreeperEntity(EntityType<? extends TreckingCreeperEntity> p_i50206_1_, Level p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ARMOR_TOUGHNESS,1.0D);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, new Random().nextInt(0, 9));
    }

    public int getTypeDir() {
        return this.entityData.get(TYPE);
    }

    public void setTypeDir(int pState) {
        this.entityData.set(TYPE, pState);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.45f;
    }

}
