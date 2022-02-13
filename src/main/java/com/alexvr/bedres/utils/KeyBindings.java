package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyMapping toggleMode;

    private static KeyMapping createBinding(String name, int key) {
        KeyMapping keyBinding = new KeyMapping(getKey(name),KeyConflictContext.IN_GAME , InputConstants.Type.KEYSYM.getOrCreate(key), getKey("category"));
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
    private static String getKey(String name) {
        return String.join(".", BedrockResources.MODID,"key", name);
    }

    public static void init() {
        toggleMode = createBinding("toggle_mode", GLFW.GLFW_KEY_K);
    }
}
