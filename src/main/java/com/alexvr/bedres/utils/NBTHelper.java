package com.alexvr.bedres.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {

    public static void setInteger(ItemStack stack,String key, int amount) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }

        nbt.putInt(key,amount);

    }

    public static void decreaseInteger(ItemStack stack,String key, int amount) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,0);
        }else {
            nbt.putInt(key, nbt.getInt(key)-amount);
        }
        stack.setTag(nbt);

    }

    public static void increaseInteger(ItemStack stack, String key, int amount) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,0);
        }

        nbt.putInt(key, nbt.getInt(key)+amount);

        stack.setTag(nbt);

    }

    public static void setString(ItemStack stack,String key, String string) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }

        nbt.putString(key,string);
        stack.setTag(nbt);

    }

    public static void setBoolean(ItemStack stack,String key, boolean amount) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        nbt.putBoolean(key,amount);
        stack.setTag(nbt);

    }

    public static int getInteger(ItemStack stack,String key) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,0);
        }
        stack.setTag(nbt);

        return nbt.getInt(key);
    }

    public static int getInteger(ItemStack stack,String key, int base) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putInt(key,base);
        }
        stack.setTag(nbt);

        return nbt.getInt(key);
    }

    public static String getString(ItemStack stack,String key) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putString(key,"");
        }
        stack.setTag(nbt);

        return nbt.getString(key);
    }

    public static String getString(ItemStack stack,String key, String base) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putString(key,base);
        }
        stack.setTag(nbt);

        return nbt.getString(key);
    }

    public static boolean getBoolean(ItemStack stack,String key) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putBoolean(key,false);
        }
        stack.setTag(nbt);
        return nbt.getBoolean(key);
    }

    public static boolean getBoolean(ItemStack stack,String key, boolean base) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putBoolean(key,base);
        }
        stack.setTag(nbt);
        return nbt.getBoolean(key);
    }

    public static void flipBoolean(ItemStack stack,String key) {
        CompoundTag nbt = stack.getTag();
        if(nbt==null) {
            nbt = new CompoundTag();
        }
        if(!nbt.contains(key)) {
            nbt.putBoolean(key,true);
        }
        stack.setTag(nbt);
        nbt.putBoolean(key, !getBoolean(stack, key));
    }
}
