package com.alexvr.bedres.entities.babyghast;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class BabyGhastRenderer extends MobRenderer<BabyGhastEntity, BabyGhastModel<BabyGhastEntity>> {

    private static final ResourceLocation RESOURCE_LOCATION =  new ResourceLocation(BedrockResources.MODID, "textures/entity/" + BedrockReferences.BABY_GHAST_REGNAME +".png");

    public BabyGhastRenderer(EntityRendererProvider.Context context) {
        super(context,  new BabyGhastModel<>(context.bakeLayer(BabyGhastModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(BabyGhastEntity pEntity) {
        return RESOURCE_LOCATION;
    }

}
