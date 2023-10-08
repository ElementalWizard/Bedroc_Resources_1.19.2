package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.HexTile;
import com.alexvr.bedres.client.renderer.*;
import com.alexvr.bedres.client.screen.FluxOverlay;
import com.alexvr.bedres.client.screen.SpellOverlay;
import com.alexvr.bedres.entities.chainedblaze.ChainedBlazeModel;
import com.alexvr.bedres.entities.chainedblaze.ChainedBlazeRenderer;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepModel;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepRenderer;
import com.alexvr.bedres.entities.sporedeity.SporeDeityModel;
import com.alexvr.bedres.entities.sporedeity.SporeDeityRenderer;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.utils.BlockRenderLayers;
import com.alexvr.bedres.utils.KeyBindings;
import com.alexvr.bedres.utils.NBTHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import static com.alexvr.bedres.items.XPMedallion.MODE;
import static com.alexvr.bedres.utils.KeyBindings.getKey;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void onItemColor(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, i) -> ((MageStaff)stack.getItem()).getColor(stack), Registration.MAGE_STAFF_ITEM.get());
        event.getItemColors().register((stack, i) -> DyeColor.MAGENTA.getMapColor().col, Registration.HEXTILE_ITEM.get());
    }

    @SubscribeEvent
    public static void onBlockColor(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((p_92646_, p_92647_, p_92648_, p_92649_) -> HexTile.getColor(p_92646_), Registration.HEXTILE_BLOCK.get());
    }
    @SubscribeEvent
    public static void registerOverlays(final RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("flux_gui", FluxOverlay.OVERLAY);
        event.registerAboveAll("spell_gui", SpellOverlay.OVERLAY);

    }
    @SubscribeEvent
    public static void onKeybindMapping(RegisterKeyMappingsEvent event) {
        KeyBindings.toggleMode = new KeyMapping(getKey("toggle_mode"), KeyConflictContext.IN_GAME , InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_K), getKey("category"));
        KeyBindings.run_alpha = new KeyMapping(getKey("alpha_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate( GLFW.GLFW_KEY_N), getKey("staff"));
        KeyBindings.run_beta = new KeyMapping(getKey("beta_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate( GLFW.GLFW_KEY_H), getKey("staff"));
        KeyBindings.run_delta = new KeyMapping(getKey("delta_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate( GLFW.GLFW_KEY_R), getKey("staff"));
        KeyBindings.run_epsilon = new KeyMapping(getKey("epsilon_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_V), getKey("staff"));
        KeyBindings.run_eta= new KeyMapping(getKey("eta_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_X), getKey("staff"));
        KeyBindings.run_gama= new KeyMapping(getKey("gama_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_P), getKey("staff"));
        KeyBindings.run_theta= new KeyMapping(getKey("theta_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_G), getKey("staff"));
        KeyBindings.run_zeta= new KeyMapping(getKey("zeta_switch"), KeyConflictContext.IN_GAME , KeyModifier.CONTROL, InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_Z), getKey("staff"));
        event.register(KeyBindings.toggleMode);
        event.register(KeyBindings.run_alpha);
        event.register(KeyBindings.run_beta);
        event.register(KeyBindings.run_delta);
        event.register(KeyBindings.run_epsilon);
        event.register(KeyBindings.run_eta);
        event.register(KeyBindings.run_gama);
        event.register(KeyBindings.run_theta);
        event.register(KeyBindings.run_zeta);
    }



    public static void init(FMLClientSetupEvent event) {
        BlockRenderLayers.init(ItemBlockRenderTypes::setRenderLayer);
        event.enqueueWork( () -> {
            BlockEntityRenderers.register(Registration.ITEM_PLATFORM_TILE.get(), ItemPlatformRenderer::new);
            BlockEntityRenderers.register(Registration.BASE_SPIKE_TILE.get(), BedrockiumTowerRenderer::new);
            BlockEntityRenderers.register(Registration.PEDESTAL_TILE.get(), BedrociumPedestalRenderer::new);
            BlockEntityRenderers.register(Registration.ENDERIAN_RITUAL_PEDESTAL_TILE.get(), EnderianRitualPedestalRenderer::new);
            BlockEntityRenderers.register(Registration.FLUXED_GRAVITY_BUBBLE_TILE.get(), FluxedGravityBubbleRenderer::new);
            ItemProperties.register(Registration.XP_MEDALLION_ITEM.get(), MODE, (itemStack, clientLevel, livingEntity, id) -> livingEntity != null ? NBTHelper.getInt(itemStack, "mode"): 0);
        });
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FluxedCreepModel.LAYER_LOCATION, FluxedCreepModel::createBodyLayer);
        event.registerLayerDefinition(ChainedBlazeModel.LAYER_LOCATION,ChainedBlazeModel::createBodyLayer);
        event.registerLayerDefinition(SporeDeityModel.ARMOR_LAYER_LOCATION,SporeDeityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.SPORE_DEITY.get(), SporeDeityRenderer::new);
        event.registerEntityRenderer(Registration.FLUXED_CREEP.get(), FluxedCreepRenderer::new);
        event.registerEntityRenderer(Registration.CHAINED_BLAZE.get(), ChainedBlazeRenderer::new);
        event.registerEntityRenderer(Registration.LIGHT_PROJ_ENTITY.get(), RenderStub::new);
    }

}
