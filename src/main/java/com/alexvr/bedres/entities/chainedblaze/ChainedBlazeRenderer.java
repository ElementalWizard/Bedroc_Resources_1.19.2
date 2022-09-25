package com.alexvr.bedres.entities.chainedblaze;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class ChainedBlazeRenderer extends MobRenderer<ChainedBlazeEntity, ChainedBlazeModel<ChainedBlazeEntity>> {

    private static final ResourceLocation RESOURCE_LOCATION =  new ResourceLocation(BedrockResources.MODID, "textures/entity/"+ BedrockReferences.CHAINED_BLAZE_REGNAME +".png");
    public ChainedBlazeRenderer(EntityRendererProvider.Context context) {
        super(context,  new ChainedBlazeModel<>(context.bakeLayer(ChainedBlazeModel.LAYER_LOCATION)), 0.5F);

    }

    @Override
    public ResourceLocation getTextureLocation(ChainedBlazeEntity pEntity) {
        return RESOURCE_LOCATION;
    }

}
