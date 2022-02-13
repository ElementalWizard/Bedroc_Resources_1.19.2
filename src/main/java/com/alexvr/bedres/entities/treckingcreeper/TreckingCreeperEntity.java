package com.alexvr.bedres.entities.treckingcreeper;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import javax.annotation.Nullable;
import java.util.*;

public class TreckingCreeperEntity extends Monster implements ContainerListener, PowerableMob {

    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> TYPEASSIGNED = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<String> DATA_ID_BACKPPACK_COLOR = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.BOOLEAN);
    public Dictionary<Biome.BiomeCategory,int[]> BIOME_FILTERS = new Hashtable<>();
    public static final int INV_CHEST_COUNT = 58;
    protected SimpleContainer inventory;
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.GUNPOWDER, Items.TNT, Registration.BEDROCK_WIRE_ITEM.get());

    private static final int FLAG_TAME = 2;
    private static final int FLAG_EATING = 16;
    private int eatingCounter;
    protected int temper;
    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;
    private int explosionRadius = 2;
    private int oldSwell;
    private int swell;
    private int maxSwell = 30;
    private boolean orderedToSit;

    public TreckingCreeperEntity(EntityType<? extends TreckingCreeperEntity> p_i50206_1_, Level p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
        BIOME_FILTERS.put(Biome.BiomeCategory.DESERT,new int[]{2,6,8});
        BIOME_FILTERS.put(Biome.BiomeCategory.NETHER,new int[]{2,3,5});
        BIOME_FILTERS.put(Biome.BiomeCategory.THEEND,new int[]{3,7});
        BIOME_FILTERS.put(Biome.BiomeCategory.BEACH,new int[]{4,6,8});
        BIOME_FILTERS.put(Biome.BiomeCategory.EXTREME_HILLS,new int[]{0,2,6});
        BIOME_FILTERS.put(Biome.BiomeCategory.FOREST,new int[]{0,2});
        BIOME_FILTERS.put(Biome.BiomeCategory.ICY,new int[]{1,3,4});
        BIOME_FILTERS.put(Biome.BiomeCategory.TAIGA,new int[]{1,3,4});
        BIOME_FILTERS.put(Biome.BiomeCategory.JUNGLE,new int[]{0,2,6});
        BIOME_FILTERS.put(Biome.BiomeCategory.MESA,new int[]{2,3,5,6});
        BIOME_FILTERS.put(Biome.BiomeCategory.MOUNTAIN,new int[]{0,2,6});
        BIOME_FILTERS.put(Biome.BiomeCategory.MUSHROOM,new int[]{3,4});
        BIOME_FILTERS.put(Biome.BiomeCategory.OCEAN,new int[]{1,2,4});
        BIOME_FILTERS.put(Biome.BiomeCategory.PLAINS,new int[]{0,1,2,6,8});
        BIOME_FILTERS.put(Biome.BiomeCategory.RIVER,new int[]{4,6,8});
        BIOME_FILTERS.put(Biome.BiomeCategory.SAVANNA,new int[]{0,2,6});
        BIOME_FILTERS.put(Biome.BiomeCategory.SWAMP,new int[]{0,4,6});
        BIOME_FILTERS.put(Biome.BiomeCategory.UNDERGROUND,new int[]{0,2,3,6,8});
        this.createInventory();
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SwellGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Monster.class, 16.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true, (p_199899_) -> !(p_199899_ instanceof TreckingCreeperEntity)));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));

    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.FLINT_AND_STEEL)) {
            this.level.playSound(pPlayer, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                this.ignite();
                itemstack.hurtAndBreak(1, pPlayer, (p_32290_) -> {
                    p_32290_.broadcastBreakEvent(pHand);
                });
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }else if (itemstack.getItem() instanceof DyeItem dyeItem) {
            this.level.playSound(pPlayer, this.getX(), this.getY(), this.getZ(), SoundEvents.DYE_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            setBackpackColor(dyeItem.getDyeColor());
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            }else {
                return InteractionResult.SUCCESS ;
            }
        }else if (this.isFood(itemstack)) {
            return this.fedFood(pPlayer, itemstack);
        }else if (!(itemstack.getItem() instanceof DyeItem)) {
            InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
            if ((!interactionresult.consumesAction()) && this.getOwnerUUID().equals(pPlayer.getUUID())) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return InteractionResult.SUCCESS;
            }
        }
        if (!this.isTamed()) {
            this.playSound(SoundEvents.HORSE_ANGRY, this.getSoundVolume(), this.getVoicePitch());
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }
        super.tick();
    }

    private void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE;
            explosion$blockinteraction = isTamed() ? Explosion.BlockInteraction.NONE : explosion$blockinteraction;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, explosion$blockinteraction);
            spawnLingeringCloud();
            setSwellDir(-1);
            this.swell = 0;
            setTarget(null);
            unignite();
            unPower();
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        return pSource.isExplosion() || pSource.equals(DamageSource.LIGHTNING_BOLT);
    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();
        if (!collection.isEmpty()) {
            Player player = level.getNearestPlayer(this,16);
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
            if (player != null){
                areaeffectcloud = new AreaEffectCloud(this.level, player.getX(), player.getY(), player.getZ());
            }
            areaeffectcloud.setRadius(2.0F);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());

            for(MobEffectInstance mobeffectinstance : collection) {
                areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
            }

            this.level.addFreshEntity(areaeffectcloud);
        }

    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        boolean flag = super.causeFallDamage(pFallDistance, pMultiplier, pSource);
        this.swell = (int)((float)this.swell + pFallDistance * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }

        return flag;
    }

    public int getMaxFallDistance() {
        return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    protected int getInventorySize() {
        return 58;
    }

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            this.setFlag(4, !this.inventory.getItem(0).isEmpty());
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ARMOR_TOUGHNESS,1.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
        this.entityData.define(TYPEASSIGNED, false);
        this.entityData.define(DATA_ID_BACKPPACK_COLOR, String.valueOf(DyeColor.BROWN.getId()));
        this.entityData.define(DATA_ID_FLAGS, (byte)0);
        this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
        this.entityData.define(DATA_SWELL_DIR, -1);
        this.entityData.define(DATA_IS_POWERED, false);
        this.entityData.define(DATA_IS_IGNITED, false);
    }

    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player && isTamed() && player.getUUID().equals(getOwnerUUID())){
            return;
        }
        if (!(pLivingEntity instanceof TreckingCreeperEntity)) {
            super.setTarget(pLivingEntity);
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.entityData.get(DATA_IS_POWERED)) {
            pCompound.putBoolean("powered", true);
        }
        pCompound.putBoolean("Sitting", this.orderedToSit);

        pCompound.putShort("Fuse", (short)this.maxSwell);
        pCompound.putByte("ExplosionRadius", (byte)this.explosionRadius);
        pCompound.putBoolean("ignited", this.isIgnited());
        pCompound.putInt("color", this.getBackpackColor().getId());
        pCompound.putBoolean("EatingHaystack", this.isEating());
        pCompound.putInt("Temper", this.getTemper());
        pCompound.putBoolean("Tame", this.isTamed());
        if (this.getOwnerUUID() != null) {
            pCompound.putUUID("Owner", this.getOwnerUUID());
        }
        ListTag listtag = new ListTag();
        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }
        pCompound.put("Items", listtag);



    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_IS_POWERED, pCompound.getBoolean("powered"));
        if (pCompound.contains("Fuse", 99)) {
            this.maxSwell = pCompound.getShort("Fuse");
        }

        if (pCompound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = pCompound.getByte("ExplosionRadius");
        }

        if (pCompound.getBoolean("ignited")) {
            this.ignite();
        }
        this.orderedToSit = pCompound.getBoolean("Sitting");
        this.setInSittingPose(this.orderedToSit);

        this.setBackpackColor(DyeColor.byId(pCompound.getInt("color")));
        this.setEating(pCompound.getBoolean("EatingHaystack"));
        this.setTemper(pCompound.getInt("Temper"));
        this.setTamed(pCompound.getBoolean("Tame"));
        this.createInventory();
        UUID uuid;
        if (pCompound.hasUUID("Owner")) {
            uuid = pCompound.getUUID("Owner");
        } else {
            String s = pCompound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }

        ListTag listtag = pCompound.getList("Items", 10);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 2 && j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.of(compoundtag));
            }
        }

        this.updateContainerEquipment();
    }
    public boolean isInSittingPose() {
        return (this.entityData.get(DATA_ID_FLAGS) & 1) != 0;
    }

    public void setInSittingPose(boolean p_21838_) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);
        if (p_21838_) {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | 1));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & -2));
        }

    }
    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }

    public void setOrderedToSit(boolean p_21840_) {
        this.orderedToSit = p_21840_;
    }
    protected boolean getFlag(int p_30648_) {
        return (this.entityData.get(DATA_ID_FLAGS) & p_30648_) != 0;
    }
    protected void setFlag(int p_30598_, boolean p_30599_) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);
        if (p_30599_) {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | p_30598_));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~p_30598_));
        }

    }

    public boolean isEating() {
        return this.getFlag(16);
    }
    public InteractionResult fedFood(Player p_30581_, ItemStack p_30582_) {
        boolean flag = this.handleEating(p_30581_, p_30582_);
        if (!p_30581_.getAbilities().instabuild) {
            p_30582_.shrink(1);
        }

        if (this.level.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
    }
    protected boolean handleEating(Player pPlayer, ItemStack pStack) {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        if (pStack.is(Registration.BEDROCK_WIRE_ITEM.get())) {
            f = 4.0F;
            i = 10;
        } else if (pStack.is(Items.GUNPOWDER)) {
            f = 2.0F;
            i = 16;
        } else if (pStack.is(Items.TNT)) {
            f = 10.0F;
            i = 2 ;
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            flag = true;
        }
        if (!isTamed() && f > 0.0F){
            if (this.random.nextInt(i) == 1) {
                this.tameWithName(pPlayer);
                this.navigation.stop();
                this.setTarget(null);
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.level.broadcastEntityEvent(this, (byte)7);
                return true;
            } else {
                this.level.broadcastEntityEvent(this, (byte)6);
            }
        }

        return flag;
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 7) {
            this.spawnTamingParticles(true);
        } else if (pId == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(pId);
        }

    }

    protected void spawnTamingParticles(boolean pPlay) {
        ParticleOptions particleoptions = ParticleTypes.HEART;
        if (!pPlay) {
            particleoptions = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }

    public void setEating(boolean p_30662_) {
        this.setFlag(16, p_30662_);
    }
    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
    }

    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }
    public void ignite() {
        this.entityData.set(DATA_IS_IGNITED, true);
    }
    public void unignite() {
        this.entityData.set(DATA_IS_IGNITED, false);
    }
    public int getTemper() {
        return this.temper;
    }
    public void setTemper(int pTemper) {
        this.temper = pTemper;
    }
    public int modifyTemper(int p_30654_) {
        int i = Mth.clamp(this.getTemper() + p_30654_, 0, this.getMaxTemper());
        this.setTemper(i);
        return i;
    }
    public int getMaxTemper() {
        return 100;
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_ID_OWNER_UUID).orElse((UUID)null);
    }
    public void setOwnerUUID(@Nullable UUID pUniqueId) {
        this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(pUniqueId));
    }

    public int getTypeDir() {
        return this.entityData.get(TYPE);
    }
    public void setTypeDir(int pState) {
        this.entityData.set(TYPE, pState);
    }

    public DyeColor getBackpackColor() {
        return DyeColor.byId(Integer.parseInt(this.entityData.get(DATA_ID_BACKPPACK_COLOR)));
    }
    public void setBackpackColor(DyeColor color) {
        this.entityData.set(DATA_ID_BACKPPACK_COLOR, String.valueOf(color.getId()));
    }

    public boolean getTypeAssignedDir() {
        return this.entityData.get(TYPEASSIGNED);
    }
    public void setTypeAssignedDir(boolean pState) {
        this.entityData.set(TYPEASSIGNED, pState);
    }

    public boolean isTamed() {
        return this.getFlag(2);
    }
    public void setTamed(boolean pTamed) {
        this.setFlag(2, pTamed);
    }
    public boolean tameWithName(Player pPlayer) {
        this.setOwnerUUID(pPlayer.getUUID());
        this.setTamed(true);
        this.level.broadcastEntityEvent(this, (byte)7);
        return true;
    }

    public float getSwelling(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }
    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }
    public void setSwellDir(int pState) {
        this.entityData.set(DATA_SWELL_DIR, pState);
    }

    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }
    public void unPower() {
        this.entityData.set(DATA_IS_POWERED,false);
    }
    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.45f;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }

    public boolean hasInventoryChanged(Container p_149512_) {
        return this.inventory != p_149512_;
    }

    public void setType(Biome.BiomeCategory biomeCategory) {
        if (!getTypeAssignedDir()){
            Random random = new Random();
            if (BIOME_FILTERS.get(biomeCategory) != null){
                for (int i = 0;i< BIOME_FILTERS.get(biomeCategory).length;i++){
                    if (random.nextInt(BIOME_FILTERS.get(biomeCategory).length) == 1){
                        setTypeDir(BIOME_FILTERS.get(biomeCategory)[i]);
                        setTypeAssignedDir(true);
                        return;
                    }
                }
                setTypeDir(BIOME_FILTERS.get(biomeCategory)[0]);
                setTypeAssignedDir(true);
            }
        }

    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null) {
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }

        }
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
        super.thunderHit(pLevel, pLightning);
        this.entityData.set(DATA_IS_POWERED, true);
    }

    @Override
    public void containerChanged(Container pInvBasic) {
        if (!this.level.isClientSide) {
            this.setFlag(4, !this.inventory.getItem(0).isEmpty());
        }
    }

    public class SwellGoal extends Goal {
        private final TreckingCreeperEntity creeper;
        @Nullable
        private LivingEntity target;

        public SwellGoal(TreckingCreeperEntity pCreeper) {
            this.creeper = pCreeper;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.creeper.getTarget();
            return this.creeper.getSwellDir() > 0 || livingentity != null && this.creeper.distanceToSqr(livingentity) < 9.0D;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.creeper.getNavigation().stop();
            this.target = this.creeper.getTarget();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.target = null;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.target == null) {
                this.creeper.setSwellDir(-1);
            } else if (this.creeper.distanceToSqr(this.target) > 49.0D) {
                this.creeper.setSwellDir(-1);
            } else if (!this.creeper.getSensing().hasLineOfSight(this.target)) {
                this.creeper.setSwellDir(-1);
            } else {
                this.creeper.setSwellDir(1);
            }
        }
    }

    public class FollowOwnerGoal extends Goal {
        public static final int TELEPORT_WHEN_DISTANCE_IS = 12;
        private static final int MIN_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 2;
        private static final int MAX_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 3;
        private static final int MAX_VERTICAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 1;
        private final TreckingCreeperEntity tamable;
        private LivingEntity owner;
        private final LevelReader level;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;
        private final boolean canFly;

        public FollowOwnerGoal(TreckingCreeperEntity pTamable, double pSpeedModifier, float pStartDistance, float pStopDistance, boolean pCanFly) {
            this.tamable = pTamable;
            this.level = pTamable.level;
            this.speedModifier = pSpeedModifier;
            this.navigation = pTamable.getNavigation();
            this.startDistance = pStartDistance;
            this.stopDistance = pStopDistance;
            this.canFly = pCanFly;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(pTamable.getNavigation() instanceof GroundPathNavigation) && !(pTamable.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (!tamable.isTamed() || Minecraft.getInstance().level == null){
                return false;
            }
            LivingEntity livingentity = Minecraft.getInstance().level.getPlayerByUUID(tamable.getOwnerUUID());
            if (livingentity == null) {
                return false;
            } else if (livingentity.isSpectator()) {
                return false;
            }else if (this.tamable.isOrderedToSit()) {
                return false;
            } else if (this.tamable.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance)) {
                return false;
            } else {
                this.owner = livingentity;
                return true;
            }

        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else if (this.tamable.isOrderedToSit()) {
                return false;
            } else {
                return !(this.tamable.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.tamable.getPathfindingMalus(BlockPathTypes.WATER);
            this.tamable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.tamable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamable.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (!this.tamable.isLeashed() && !this.tamable.isPassenger()) {
                    if (this.tamable.distanceToSqr(this.owner) >= 144.0D) {
                        this.teleportToOwner();
                    } else {
                        this.navigation.moveTo(this.owner, this.speedModifier);
                    }

                }
            }
        }

        private void teleportToOwner() {
            BlockPos blockpos = this.owner.blockPosition();

            for(int i = 0; i < 10; ++i) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
                if (flag) {
                    return;
                }
            }

        }

        private boolean maybeTeleportTo(int pX, int pY, int pZ) {
            if (Math.abs((double)pX - this.owner.getX()) < 2.0D && Math.abs((double)pZ - this.owner.getZ()) < 2.0D) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
                return false;
            } else {
                this.tamable.moveTo((double)pX + 0.5D, (double)pY, (double)pZ + 0.5D, this.tamable.getYRot(), this.tamable.getXRot());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pPos) {
            BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pPos.mutable());
            if (blockpathtypes != BlockPathTypes.WALKABLE) {
                return false;
            } else {
                BlockState blockstate = this.level.getBlockState(pPos.below());
                if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockpos = pPos.subtract(this.tamable.blockPosition());
                    return this.level.noCollision(this.tamable, this.tamable.getBoundingBox().move(blockpos));
                }
            }
        }

        private int randomIntInclusive(int pMin, int pMax) {
            return this.tamable.getRandom().nextInt(pMax - pMin + 1) + pMin;
        }
    }

    public class SitWhenOrderedToGoal extends Goal {
        private final TreckingCreeperEntity mob;

        public SitWhenOrderedToGoal(TreckingCreeperEntity pMob) {
            this.mob = pMob;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.mob.isOrderedToSit();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (!this.mob.isTamed() || Minecraft.getInstance().level == null) {
                return false;
            } else if (this.mob.isInWaterOrBubble()) {
                return false;
            } else if (!this.mob.isOnGround()) {
                return false;
            } else {
                LivingEntity livingentity = Minecraft.getInstance().level.getPlayerByUUID(mob.getOwnerUUID());
                if (livingentity == null) {
                    return false;
                } else {
                    return this.mob.distanceToSqr(livingentity) < 144.0D && this.mob.isOrderedToSit();
                }
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.mob.getNavigation().stop();
            this.mob.setInSittingPose(true);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.mob.setInSittingPose(false);
        }
    }
}
