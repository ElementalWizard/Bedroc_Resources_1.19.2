package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.commands.ModCommands;
import com.alexvr.bedres.world.ModWorldgen;
import com.alexvr.bedres.world.dimension.MysBiomeProvider;
import com.alexvr.bedres.world.dimension.MysChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.alexvr.bedres.BedrockResources.MODID;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {
    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.BEDROCK_WIRE_BLOCK.get());
        }
    };

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(BedrockResources.MODID, "chunkgen"),
                    MysChunkGenerator.CODEC);
            Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(BedrockResources.MODID, "biomes"),
                    MysBiomeProvider.CODEC);
            ModWorldgen.onCommonSetup();

        });
    }

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

}
