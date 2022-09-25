package com.alexvr.bedres.entities.sporedeity;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class SporeDietyPowerLayer extends EnergySwirlLayer<SporeDeityEntity, SporeDeityModel<SporeDeityEntity>> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/aura1.png");
    private final SporeDeityModel<SporeDeityEntity> model;
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BedrockResources.MODID, "spore_deity"), "armor");

    public SporeDietyPowerLayer(RenderLayerParent<SporeDeityEntity, SporeDeityModel<SporeDeityEntity>> p_174471_, EntityModelSet p_174472_) {
        super(p_174471_);
        this.model = new SporeDeityModel<>(p_174472_.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
    }

    protected float xOffset(float p_116683_) {
        return p_116683_ * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    protected EntityModel<SporeDeityEntity> model() {
        return this.model;
    }
}
