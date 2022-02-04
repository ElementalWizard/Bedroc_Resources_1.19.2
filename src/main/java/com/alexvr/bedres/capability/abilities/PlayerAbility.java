package com.alexvr.bedres.capability.abilities;

import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.setup.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;

public class PlayerAbility implements  IPlayerAbility{

    private String axe = ModConfig.DEF_AXE.get(),pick= ModConfig.DEF_PICK.get(),shovel= ModConfig.DEF_SHOVEL.get(),sword= ModConfig.DEF_SWORD.get(),hoe= ModConfig.DEF_HOE.get(),name = "no",result="";
    private int ritualTimer =1,totalRitual=1;
    private double jump= ModConfig.DEF_JUMP.get(),speed = ModConfig.DEF_SPEED.get(), flux = ModConfig.DEF_FLUX.get(), maxflux = ModConfig.DEF_MAXFLUX.get(), fluxcooldown = ModConfig.DEF_FLUXCOOLDOWN.get();
    private boolean ritual = false;
    private boolean checking = false;

    private boolean fall_damage = true;
    private ArrayList<EnderianRitualPedestalTile> pedestals;
    private double FOV;
    private Vec3 lookingAt;

    public static Capability<IPlayerAbility> ABILITY_CAPABILITY = null;

    @Override
    public String getNAme() {
        return name;
    }

    @Override
    public String getAxe() {
        return axe;
    }

    @Override
    public String getPick() {
        return pick;
    }

    @Override
    public String getShovel() {
        return shovel;
    }

    @Override
    public String getSword() {
        return sword;
    }

    @Override
    public String getHoe() {
        return hoe;
    }

    @Override
    public Double getFlux() {
        return flux;
    }

    @Override
    public Double getMaxFlux() {
        return maxflux;
    }

    @Override
    public Double getFluxCooldown() {
        return fluxcooldown;
    }

    @Override
    public void setMaxFlux(Double amount) {
        maxflux = amount;
    }

    @Override
    public void setFlux(Double amount) {
        flux=Math.min(amount,getMaxFlux());
    }

    @Override
    public void addFlux(Double amount) {
        flux+=getFlux()+amount<=getMaxFlux()?amount:0;
    }

    @Override
    public void removeFlux(Double amount) {
        flux-=getFlux()-amount>=0?amount:0;
    }

    @Override
    public void setFluxCooldown(Double amount) {
        fluxcooldown=amount;
    }

    @Override
    public void removeFluxCooldown(Double amount) {
        fluxcooldown-=amount;
    }

    @Override
    public double getMiningSpeedBoost() {
        return speed;
    }

    @Override
    public int getRitualTimer() {
        return ritualTimer;
    }

    @Override
    public int getRitualTotalTimer() {
        return totalRitual;
    }

    @Override
    public ArrayList<EnderianRitualPedestalTile> getListOfPedestals() {
        return pedestals;
    }

    @Override
    public String getRitualCraftingResult() {
        return result;
    }

    @Override
    public double getJumpBoost() {
        return jump;
    }

    @Override
    public boolean getInRitual() {
        return ritual;
    }

    @Override
    public boolean getChecking() {
        return checking;
    }

    @Override
    public boolean takesFalldamage() {
        return fall_damage;
    }

    @Override
    public double getFOV() {
        return FOV;
    }

    @Override
    public Vec3 getlookPos() {
        return lookingAt;
    }

    @Override
    public void setAxe(String name) {
        axe=name;
    }

    @Override
    public void setPick(String name) {
        pick=name;

    }

    @Override
    public void setShovel(String name) {
        shovel=name;

    }

    @Override
    public void setSword(String name) {
        sword=name;

    }

    @Override
    public void setHoe(String name) {
        hoe=name;

    }

    @Override
    public void setname(String name) {
        this.name=name;
    }

    @Override
    public void setTakesFallDamage(Boolean takesFallDamage) {
        fall_damage = takesFallDamage;
    }

    @Override
    public void setMiningSpeedBoost(double amount) {
        speed=amount;

    }

    @Override
    public void setJumpBoost(double amount) {
        jump=amount;

    }


    @Override
    public void addMiningSpeed(double amount) {
        speed+=amount;
    }

    @Override
    public void addJump(double amount) {
        jump+=amount;
    }

    @Override
    public void flipRitual() {
        ritual=!ritual;
    }

    @Override
    public void flipFallDamage() {
        fall_damage = !fall_damage;
    }

    @Override
    public void setRitualTimer(int amount) {
        ritualTimer = amount;
    }

    @Override
    public void setRitualTotalTimer(int amount) {
        totalRitual = amount;
    }

    @Override
    public void incrementRitualTimer() {
        ritualTimer++;
    }

    @Override
    public void flipChecking() {
        checking=!checking;
    }

    @Override
    public void setRitualPedestals(ArrayList<EnderianRitualPedestalTile> pedestals) {
        this.pedestals = pedestals;
    }

    @Override
    public void setRitualCraftingResult(String result) {
        this.result = result;
    }

    @Override
    public void setFOV(double FOV) {
        this.FOV = FOV;
    }

    @Override
    public void setLookPos(Vec3 vec3d) {
        lookingAt = vec3d;
    }

    @Override
    public void addLookPos(double x, double y, double z) {
        lookingAt.add(x,y,z);
    }

    public void clear(){
        axe = "no";
        pick= "no";
        shovel= "no";
        sword= "no";
        hoe= "no";
        name = "no";
        result="";
        speed =0;
        ritualTimer =1;
        totalRitual=1;
        jump=0;
        ritual = false;
        checking = false;
        flux = ModConfig.DEF_FLUX.get();
        maxflux = ModConfig.DEF_MAXFLUX.get();
        fluxcooldown = ModConfig.DEF_FLUXCOOLDOWN.get();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("axe",getAxe());
        tag.putString("pick",getPick());
        tag.putString("shovel",getShovel());
        tag.putString("sword",getSword());
        tag.putString("hoe",getHoe());
        tag.putString("result",getRitualCraftingResult());
        tag.putDouble("speed",getMiningSpeedBoost());
        tag.putInt("ritualTimer",getRitualTimer());
        tag.putInt("totalRitual",getRitualTotalTimer());
        tag.putDouble("jump",getJumpBoost());
        tag.putBoolean("ritual",getInRitual());
        tag.putBoolean("checking",getChecking());
        tag.putDouble("FOV",getFOV());
        if (getlookPos() != null) {
            tag.putDouble("lookingAtX", getlookPos().x);
            tag.putDouble("lookingAtY", getlookPos().y);
            tag.putDouble("lookingAtZ", getlookPos().z);
        }
        tag.putDouble("flux",getFlux());
        tag.putDouble("maxflux",getMaxFlux());
        tag.putDouble("fluxcooldown",getFluxCooldown());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setAxe(nbt.getString("axe"));
        setPick(nbt.getString("pick"));
        setShovel(nbt.getString("shovel"));
        setSword(nbt.getString("sword"));
        setHoe(nbt.getString("hoe"));
        setMiningSpeedBoost(nbt.getDouble("speed"));
        setJumpBoost(nbt.getDouble("jump"));
        setRitualTimer(nbt.getInt("ritualTimer"));
        setRitualTotalTimer(nbt.getInt("totalRitual"));
        setRitualCraftingResult(nbt.getString("result"));
        setFOV(nbt.getDouble("FOV"));
        if (nbt.contains("lookingAtX")) {
            setLookPos(new Vec3(( nbt).getDouble("lookingAtX"), ( nbt).getDouble("lookingAtY"), ( nbt).getDouble("lookingAtZ")));
        }
        setFlux(nbt.getDouble("flux"));
        setMaxFlux(nbt.getDouble("maxflux"));
        setFluxCooldown(nbt.getDouble("fluxcooldown"));
    }
}
