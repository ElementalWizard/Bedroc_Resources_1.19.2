package com.alexvr.bedres.entities.fluxedcreep;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class FluxedCreepEntity extends Ghast {


    public FluxedCreepEntity(EntityType<? extends FluxedCreepEntity> p_i50206_1_, Level p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
    }

    protected void registerGoals() {

        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 16));
        super.registerGoals();
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.remove(RemovalReason.DISCARDED);
        }
        BlockPos pos = new BlockPos((int) this.position().x, (int)this.position().y, (int)this.position().z);
        //Minecraft.getInstance().levelRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX() + 0.35f,pos.getY() + 0.35f,pos.getZ()+ 0.35f,0,0,0);

    }


    @ParametersAreNonnullByDefault
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }else {
            return super.hurt(source, amount);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Registration.FLUXED_CREEP_TYPE.getStepSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return Registration.FLUXED_CREEP_TYPE.getFallSound();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Registration.FLUXED_CREEP_TYPE.getHitSound();
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 75.0)
                .add(Attributes.FOLLOW_RANGE, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ARMOR_TOUGHNESS,1.0D)
                .add(Attributes.ATTACK_KNOCKBACK,4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE,12.0D);
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public MobType getMobType() {
       return MobType.UNDEFINED;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.5f;
    }


}
