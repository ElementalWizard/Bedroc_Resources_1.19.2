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
import net.minecraft.world.level.biome.Biome;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

public class TreckingCreeperEntity extends Creeper {

    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> TYPEASSIGNED = SynchedEntityData.defineId(TreckingCreeperEntity.class, EntityDataSerializers.BOOLEAN);
    public Dictionary<Biome.BiomeCategory,int[]> BIOME_FILTERS = new Hashtable<>();

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
    }

    public int getTypeDir() {
        return this.entityData.get(TYPE);
    }

    public void setTypeDir(int pState) {
        this.entityData.set(TYPE, pState);
    }

    public boolean getTypeAssignedDir() {
        return this.entityData.get(TYPEASSIGNED);
    }

    public void setTypeAssignedDir(boolean pState) {
        this.entityData.set(TYPEASSIGNED, pState);
    }
    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.45f;
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
}
