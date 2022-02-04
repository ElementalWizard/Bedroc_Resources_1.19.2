package com.alexvr.bedres.capability.abilities;

import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public interface IPlayerAbility extends INBTSerializable<CompoundTag>
{


    String getNAme();
    String getAxe();
    String getPick();
    String getShovel();
    String getSword();
    String getHoe();

    Double getFlux();
    Double getMaxFlux();
    Double getFluxCooldown();
    void setMaxFlux(Double amount);
    void setFlux(Double amount);
    void addFlux(Double amount);
    void removeFlux(Double amount);
    void setFluxCooldown(Double amount);
    void removeFluxCooldown(Double amount);

    double getMiningSpeedBoost();
    int getRitualTimer();
    int getRitualTotalTimer();
    ArrayList<EnderianRitualPedestalTile> getListOfPedestals();
    String getRitualCraftingResult();

    double getJumpBoost();

    boolean getInRitual();
    boolean getChecking();
    boolean takesFalldamage();

    double getFOV();

    Vec3 getlookPos();


    void setAxe(String name);
    void setPick(String name);
    void setShovel(String name);
    void setSword(String name);
    void setHoe(String name);
    void setname(String name);
    void setTakesFallDamage(Boolean takesFallDamage);

    void setMiningSpeedBoost(double amount);
    void setJumpBoost(double amount);

    void addMiningSpeed(double amount);
    void addJump(double amount);

    void flipRitual();
    void flipFallDamage();

    void setRitualTimer(int amount);
    void setRitualTotalTimer(int amount);
    void incrementRitualTimer();
    void flipChecking();
    void setRitualPedestals(ArrayList<EnderianRitualPedestalTile> pedestals);
    void setRitualCraftingResult(String result);
    void setFOV(double FOV);
    void setLookPos(Vec3 vec3d);
    void addLookPos(double x, double y, double z);

    void clear();
}
