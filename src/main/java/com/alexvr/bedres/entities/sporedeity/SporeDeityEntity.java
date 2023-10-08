package com.alexvr.bedres.entities.sporedeity;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class SporeDeityEntity extends Monster implements PowerableMob {

    private boolean ATTACKING = false;
    public static final EntityDataAccessor<Boolean> DATA_ATTACKING = SynchedEntityData.defineId(SporeDeityEntity.class, EntityDataSerializers.BOOLEAN);

    private int counter = 0;
    public SporeDeityEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        if (GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }
    }
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (player) -> Math.abs(player.getY() - this.getY()) <= 4.0D));
        this.targetSelector.addGoal(1,  new MeleeAttackGoal(this,1.25,false));
        this.targetSelector.addGoal(1,  new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(2,  new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(3,   new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACKING, false);

    }

    public boolean isAttacking() {
        return this.entityData.get(DATA_ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        counter = 60;
        this.entityData.set(DATA_ATTACKING, attacking);
    }

    public void tick() {
        super.tick();
        if (!level().isClientSide() && this.getTarget() != null && ! isAttacking()) {
            LivingEntity livingentity = this.getTarget();
            int chance = 400;
            if (new Random().nextInt(chance) <= 1) {
                AOECloud(livingentity.getX(),livingentity.getY(),livingentity.getZ());
            }
            else if (new Random().nextInt(chance)  <= 3) {
                //spawnRandomEffectBall(livingentity);
            }
            else if (new Random().nextInt(chance) <= 6) {
                spawnFireBall(livingentity);
            }
            else if (new Random().nextInt(chance) <= 8) {
                teleport();
            }
        }else if (!level().isClientSide() &&isAttacking()){
            if (counter <= 0){
                setAttacking(false);
            }else{
                counter--;
            }
        }

    }

    private void AOECloud(double x,double y, double z) {
        setAttacking(true);
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level(), x,y,z);
        areaeffectcloudentity.setOwner(this);
        areaeffectcloudentity.setParticle(ParticleTypes.SQUID_INK);
        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setDuration(600);
        areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 4));
        areaeffectcloudentity.setFixedColor(8421504);
        areaeffectcloudentity.moveTo(x, y, z);
        this.level().levelEvent(2006,new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), 0);
        this.level().addFreshEntity(areaeffectcloudentity);
    }

    private void spawnRandomEffectBall(LivingEntity livingentity) {
        setAttacking(true);
        Vec3 vec3d = this.getEyePosition(1.0F);
        level().levelEvent(null, 1016, this.getOnPos(), 0);
        DragonFireball fireballentity = new DragonFireball(level(), this, getX(), getY()+2, getZ());

        //fireballentity.setDye(new ItemStack(Items.MAGENTA_DYE));
        //fireballentity.explosionPower = 0;
        fireballentity.moveTo(new BlockPos((int) (this.getX() + vec3d.x * 2.0D + (new Random().nextInt(4)-2)), (int) (this.getY() + (this.getEyeHeight())  + 1.5D), (int) (this.getZ() + vec3d.z * 2.0D + (new Random().nextInt(4)-2))),0,0);
        //ItemStack stack = new ItemStack(Items.MAGENTA_DYE);
        //stack = getRandomDye(stack);
        //((EffectBallEntity) Objects.requireNonNull(ModEntities.effectBallEntityEntityType.spawn(world, null, (PlayerEntity) livingentity, fireballentity.getPosition(), SpawnReason.SPAWN_EGG, true, true))).setDye(stack);
        level().addFreshEntity(fireballentity);
    }

    private ItemStack getRandomDye(ItemStack stack) {
        switch (new Random().nextInt(8)+1){
            case 1:
                stack = new ItemStack(Items.MAGENTA_DYE);
                break;
            case 2:
                stack = new ItemStack(Items.BLACK_DYE);
                break;
            case 3:
                stack = new ItemStack(Items.GREEN_DYE);
                break;
            case 4:
                stack = new ItemStack(Items.WHITE_DYE);
                break;
            case 5:
                stack = new ItemStack(Items.BROWN_DYE);
                break;
            case 6:
                stack = new ItemStack(Items.PURPLE_DYE);
                break;
            case 7:
                stack = new ItemStack(Items.YELLOW_DYE);
                break;
            case 8:
                stack = new ItemStack(Items.RED_DYE);
                break;

        }
        return stack;
    }

    private void teleport() {
        setAttacking(true);
        boolean success = false;
        for (int i = 0; i<50;i++){
            BlockPos pos = getOnPos().south(new Random().nextInt(3)-3).east(new Random().nextInt(3)-3);
            if (level().getBlockState(pos).getBlock() == Blocks.AIR){
                moveTo(pos.getX(),pos.getY(),pos.getZ());
                success = true;
                break;
            }else if (level().getBlockState(pos.relative(Direction.UP)).getBlock() == Blocks.AIR){
                moveTo(pos.getX(),pos.getY()+1,pos.getZ());
                success = true;
                break;
            }
        }
        if (success){
            this.level().playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        }
    }

    private void spawnFireBall(LivingEntity livingentity) {
        setAttacking(true);
        double d1 = 4.0D;
        Vec3 vec3 = this.getViewVector(1.0F);
        double d2 = livingentity.getX() - (this.getX() + vec3.x * 4.0D);
        double d3 = livingentity.getY(0.5D) - (0.5D + this.getY(0.5D));
        double d4 = livingentity.getZ() - (this.getZ() + vec3.z * 4.0D);
        if (!this.isSilent()) {
            level().levelEvent((Player)null, 1016, this.blockPosition(), 0);
        }

        LargeFireball largefireball = new LargeFireball(level(), this, d2, d3, d4, 2);
        largefireball.setPos(this.getX() + vec3.x * 4.0D, this.getY(0.5D) + 0.5D, largefireball.getZ() + vec3.z * 4.0D);
        level().addFreshEntity(largefireball);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        setAttacking(tag.getBoolean("attacking"));
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putBoolean("attacking", isAttacking());
        return super.save(tag);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.ARMOR_TOUGHNESS,6.0D)
                .add(Attributes.ATTACK_KNOCKBACK,4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE,12.0D);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_FIRE) ||
                damageSource.is(DamageTypeTags.IS_DROWNING) || damageSource.is(DamageTypeTags.IS_FREEZING) ||
                damageSource.is(DamageTypes.IN_WALL) || damageSource.is(DamageTypes.WITHER) ||
                damageSource.is(DamageTypes.FALL) || damageSource.is(DamageTypes.CRAMMING) || damageSource.is(DamageTypes.CACTUS)){
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }else {
            return super.hurt(source, amount);
        }
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.MASTER;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_CAVE.get();
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
        return 1.75f;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        ItemEntity drop = new ItemEntity(level(), getX(),getY(),getZ(),new ItemStack(Registration.NEBULA_HEART_ITEM.get(),1));
        level().addFreshEntity(drop);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_DEATH;
    }

    @Override
    public boolean isPowered() {
        return true;
    }
}
