package com.alexvr.bedres;

import com.alexvr.bedres.setup.ClientSetup;
import com.alexvr.bedres.setup.ModSetup;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.world.ModWorldgen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BedrockResources.MODID)
public class BedrockResources {

    public static final String MODID = "bedres";

    public BedrockResources() {
        Registration.init();

        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        event.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> event.addListener(ClientSetup::init));
        MinecraftForge.EVENT_BUS.register(new ModWorldgen());
    }

}
