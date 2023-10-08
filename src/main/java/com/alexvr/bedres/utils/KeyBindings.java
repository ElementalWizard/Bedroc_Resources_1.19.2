package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.client.KeyMapping;

public class KeyBindings {
    public static KeyMapping toggleMode;
    public static KeyMapping run_alpha;
    public static KeyMapping run_beta;
    public static KeyMapping run_delta;
    public static KeyMapping run_epsilon;
    public static KeyMapping run_eta;
    public static KeyMapping run_gama;
    public static KeyMapping run_theta;
    public static KeyMapping run_zeta;

    public static String getKey(String name) {
        return String.join(".", BedrockResources.MODID,"key", name);
    }

}
