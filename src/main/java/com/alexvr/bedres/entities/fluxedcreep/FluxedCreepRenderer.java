package com.alexvr.bedres.entities.fluxedcreep;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class FluxedCreepRenderer extends MobRenderer<FluxedCreepEntity, FluxedCreepModel<FluxedCreepEntity>> {

    private static final ResourceLocation RESOURCE_LOCATION =  new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ BedrockReferences.FLUXED_CREEP_REGNAME +".png");
    public FluxedCreepRenderer(EntityRendererProvider.Context context) {
        super(context,  new FluxedCreepModel<>(context.bakeLayer(FluxedCreepModel.FLUXED_CREEP_LAYER)), 0.5F);
        this.addLayer(new FluxedCreepOuterLayer<>(this, context.getModelSet()));

    }

    @Override
    public ResourceLocation getTextureLocation(FluxedCreepEntity pEntity) {
        return RESOURCE_LOCATION;
    }

//    protected void preRenderCallback(FluxedCreepEntity entitylivingbaseIn, float partialTickTime) {
//        EffectBallRenderer.preRenderCallbackTransparentEntity();
//    }
}
