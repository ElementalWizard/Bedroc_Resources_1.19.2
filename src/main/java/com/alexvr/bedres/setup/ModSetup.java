package com.alexvr.bedres.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.alexvr.bedres.BedrockResources.MODID;

public class ModSetup {
    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.BEDROCK_WIRE_BLOCK.get());
        }
    };


    public static void init(FMLCommonSetupEvent event) {

    }

}
