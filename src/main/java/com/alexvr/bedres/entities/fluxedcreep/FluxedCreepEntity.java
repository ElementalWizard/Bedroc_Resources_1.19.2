package com.alexvr.bedres.entities.fluxedcreep;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;
import java.util.Random;

import static net.minecraft.util.Mth.ceil;

public class FluxedCreepEntity extends FlyingMob implements Enemy {

    private static final EntityDataAccessor<Boolean> ATTACKING =  SynchedEntityData.defineId(FluxedCreepEntity.class, EntityDataSerializers.BOOLEAN);

    public FluxedCreepEntity(EntityType<? extends FlyingMob> p_i50206_1_, Level p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
        this.moveControl = new MoveHelperController(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomFlyGoal(this));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 16));
        this.goalSelector.addGoal(1, new FireballAttackGoal(this));
        this.goalSelector.addGoal(1, new AttackGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_213812_1_) -> Math.abs(p_213812_1_.getY() - this.getY()) <= 4.0D));
    }

    boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    private void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }


    public void tick() {
        super.tick();
        if (!this.level.isClientSide() && this.level.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove(RemovalReason.DISCARDED);
        }
        BlockPos pos = new BlockPos(this.getOnPos());
        Minecraft.getInstance().levelRenderer.addParticle(ParticleTypes.LARGE_SMOKE,true,pos.getX(),pos.getY()+.6,pos.getZ(),0,0,0);

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);

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

    static class MoveHelperController extends MoveControl {
        private final FluxedCreepEntity parentEntity;
        private int courseChangeCooldown;

        MoveHelperController(FluxedCreepEntity ghast) {
            super(ghast);
            this.parentEntity = ghast;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
                    Vec3 vec3d = new Vec3(this.wantedX - this.parentEntity.getX(), this.getWantedX() - this.parentEntity.getY(), this.getWantedZ() - this.parentEntity.getZ());
                    double d0 = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.func_220673_a(vec3d, ceil(d0))) {
                        this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vec3d.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }
            }
        }

        private boolean func_220673_a(Vec3 p_220673_1_, int p_220673_2_) {
            AABB axisalignedbb = this.parentEntity.getBoundingBox();

            for(int i = 1; i < p_220673_2_; ++i) {
                axisalignedbb = axisalignedbb.move(p_220673_1_);
                if (!this.parentEntity.level.noCollision(this.parentEntity)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class RandomFlyGoal extends Goal {
        private final FluxedCreepEntity parentEntity;

        RandomFlyGoal(FluxedCreepEntity ghast) {
            this.parentEntity = ghast;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Random random = this.parentEntity.getRandom();
            double d0 = this.parentEntity.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
            double d2 = this.parentEntity.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 0.4D);
        }

        public void stop() {
            this.parentEntity.getNavigation().stop();
        }

        @Override
        public boolean canUse() {
            MoveControl movementcontroller = this.parentEntity.getMoveControl();
            if (!movementcontroller.hasWanted()) {
                return true;
            } else {
                double d0 = movementcontroller.getWantedX() - this.parentEntity.getX();
                double d1 = movementcontroller.getWantedY() - this.parentEntity.getY();
                double d2 = movementcontroller.getWantedZ() - this.parentEntity.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }
    }

    static class AttackGoal extends Goal {
        private final FluxedCreepEntity parentEntity;
        int attackTimer;

        AttackGoal(FluxedCreepEntity ghast) {
            this.parentEntity = ghast;
        }

        @Override
        public boolean canUse() {
            return this.parentEntity.getTarget() != null;
        }

        public boolean canContinueToUse() {
            return this.parentEntity.getTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.attackTimer = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.parentEntity.setAttacking(false);
        }
        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.parentEntity.getTarget();
            assert livingentity != null;
            if ((livingentity.distanceTo(this.parentEntity) < 4096.0D) && this.parentEntity.canBeSeenByAnyone()) {
                if (livingentity.distanceTo(this.parentEntity) < 1) {
                    livingentity.hurt(DamageSource.MAGIC, 4);
                    this.parentEntity.setAttacking(false);

                }
                this.parentEntity.setAttacking(true);

            }
        }
    }

    static class FireballAttackGoal extends Goal {
        private final FluxedCreepEntity parentEntity;
        int attackTimer;

        FireballAttackGoal(FluxedCreepEntity ghast) {
            this.parentEntity = ghast;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return this.parentEntity.getTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.attackTimer = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.parentEntity.setAttacking(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.parentEntity.getTarget();
            assert livingentity != null;
            if (livingentity.distanceTo(this.parentEntity) > 1028.0D && livingentity.distanceTo(this.parentEntity) < 4096.0D && this.parentEntity.canBeSeenByAnyone()) {
                Level world = this.parentEntity.level;
                ++this.attackTimer;
                if (this.attackTimer == 10) {
                    world.levelEvent(null, 1015, this.parentEntity.getOnPos(), 0);
                }

                if (this.attackTimer == 20) {
                    Vec3 vec3d = this.parentEntity.getViewVector(1.0F);
                    double d2 = livingentity.getX() - (this.parentEntity.getX() + vec3d.x * 4.0D);
                    double d3 = livingentity.getBoundingBox().minY + (double)(livingentity.getEyeHeight() / 2.0F) - (0.5D + this.parentEntity.getY() + (double)(this.parentEntity.getEyeHeight() / 2.0F));
                    double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vec3d.z * 4.0D);
                    world.levelEvent(null, 1016, this.parentEntity.getOnPos(), 0);
                    LargeFireball fireballentity = new LargeFireball(world, this.parentEntity, d2, d3, d4,1);
                    fireballentity.moveTo(new BlockPos( this.parentEntity.getX() + vec3d.x * 4.0D,this.parentEntity.getY() + (double)(this.parentEntity.getEyeHeight() / 2.0F) + 0.5D,this.parentEntity.getZ() + vec3d.z * 4.0D),0,0);
                    world.addFreshEntity(fireballentity);
                    this.attackTimer = -40;
                }
            } else if (this.attackTimer > 0) {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }

}
