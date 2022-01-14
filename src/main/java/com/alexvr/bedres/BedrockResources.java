package com.alexvr.bedres;

import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.setup.ClientSetup;
import com.alexvr.bedres.setup.Config;
import com.alexvr.bedres.setup.ModSetup;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.world.ModWorldgen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;


@Mod(BedrockResources.MODID)
public class BedrockResources {

    public static final String MODID = "bedres";
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public BedrockResources() {
        ModSetup.setup();
        Config.register();
        Registration.init();
        ModRecipeRegistry.register();
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();

        event.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> event.addListener(ClientSetup::init));

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.register(new ModWorldgen());
    }


    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                                             BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

}
