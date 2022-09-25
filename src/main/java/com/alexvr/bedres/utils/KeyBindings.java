package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyMapping toggleMode;

    public static String getKey(String name) {
        return String.join(".", BedrockResources.MODID,"key", name);
    }

}
