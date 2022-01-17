package com.alexvr.bedres.capability.abilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerAbilityProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<IPlayerAbility> PLAYER_ABILITY_CAPABILITY = PlayerAbility.ABILITY_CAPABILITY;

    private final PlayerAbility instance = new PlayerAbility();
    private final LazyOptional<IPlayerAbility> capability = LazyOptional.of(() -> instance);

    public boolean hasCapability(Capability<?> capability, Direction facing) {
        return capability == PLAYER_ABILITY_CAPABILITY;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITY_CAPABILITY) {
            return capability.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT(nbt);
    }

}
