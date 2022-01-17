package com.alexvr.bedres.capability.abilities;

import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public interface IPlayerAbility {


    String getNAme();
    String getAxe();
    String getPick();
    String getShovel();
    String getSword();
    String getHoe();

    double getMiningSpeedBoost();
    int getRitualTimer();
    int getRitualTotalTimer();
    ArrayList<EnderianRitualPedestalTile> getListOfPedestals();
    String getRitualCraftingResult();

    double getJumpBoost();
    double getGravityMultiplier();

    boolean getInRitual();
    boolean getChecking();

    double getFOV();

    Vec3 getlookPos();


    void setAxe(String name);
    void setPick(String name);
    void setShovel(String name);
    void setSword(String name);
    void setHoe(String name);
    void setname(String name);

    void setMiningSpeedBoost(double amount);
    void setJumpBoost(double amount);
    void setGRavityMultiplier(double amount);

    void addMiningSpeed(double amount);
    void addJump(double amount);
    void addGrav(double amount);

    void flipRitual();

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
