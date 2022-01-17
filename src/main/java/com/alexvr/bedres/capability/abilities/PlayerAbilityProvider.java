package com.alexvr.bedres.capability.abilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerAbilityProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<IPlayerAbility> PLAYER_ABILITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});;

    private IPlayerAbility instance = new PlayerAbility();

    public boolean hasCapability(Capability<?> capability, Direction facing) {
        return capability == PLAYER_ABILITY_CAPABILITY;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITY_CAPABILITY) {
            return (LazyOptional<T>) LazyOptional.of(PlayerAbility::new);
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("axe",instance.getAxe());
        tag.putString("pick",instance.getPick());
        tag.putString("shovel",instance.getShovel());
        tag.putString("sword",instance.getSword());
        tag.putString("hoe",instance.getHoe());
        tag.putString("result",instance.getRitualCraftingResult());
        tag.putDouble("speed",instance.getMiningSpeedBoost());
        tag.putInt("ritualTimer",instance.getRitualTimer());
        tag.putInt("totalRitual",instance.getRitualTotalTimer());
        tag.putDouble("gravilty",instance.getGravityMultiplier());
        tag.putDouble("jump",instance.getJumpBoost());
        tag.putBoolean("ritual",instance.getInRitual());
        tag.putBoolean("checking",instance.getChecking());
        tag.putDouble("FOV",instance.getFOV());
        if (instance.getlookPos() != null) {
            tag.putDouble("lookingAtX", instance.getlookPos().x);
            tag.putDouble("lookingAtY", instance.getlookPos().y);
            tag.putDouble("lookingAtZ", instance.getlookPos().z);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.setAxe((nbt).getString("axe"));
        instance.setPick((nbt).getString("pick"));
        instance.setShovel((nbt).getString("shovel"));
        instance.setSword((nbt).getString("sword"));
        instance.setHoe((nbt).getString("hoe"));
        instance.setMiningSpeedBoost((nbt).getDouble("speed"));
        instance.setJumpBoost((nbt).getDouble("jump"));
        instance.setGRavityMultiplier((nbt).getDouble("gravilty"));
        instance.setRitualTimer((nbt).getInt("ritualTimer"));
        instance.setRitualTotalTimer((nbt).getInt("totalRitual"));
        instance.setRitualCraftingResult((nbt).getString("result"));
        instance.setFOV((nbt).getDouble("FOV"));
        if ((nbt).contains("lookingAtX")) {
            instance.setLookPos(new Vec3(( nbt).getDouble("lookingAtX"), ( nbt).getDouble("lookingAtY"), ( nbt).getDouble("lookingAtZ")));
        }
    }

}
