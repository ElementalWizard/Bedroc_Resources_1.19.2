package com.alexvr.bedres.client.particles;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.client.particles.lightParticle.LightParticleType;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ParticleHandler {

    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent evt) {
        Minecraft.getInstance().particleEngine.register(Registration.LIGHT_PARTICLE_TYPE.get(), LightParticleType.LightParticleFactory::new);
    }

}