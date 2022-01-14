package com.alexvr.bedres.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {

    public static void setInteger(ItemStack stack,String key, int amount) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(key,amount);

    }

    public static boolean getBoolean(ItemStack stack,String key) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean(key);
    }

}
