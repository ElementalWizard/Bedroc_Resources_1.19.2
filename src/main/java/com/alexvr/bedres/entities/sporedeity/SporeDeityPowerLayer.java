package com.alexvr.bedres.entities.sporedeity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.alexvr.bedres.entities.sporedeity.SporeDeityModel.ARMOR_LAYER_LOCATION;

@OnlyIn(Dist.CLIENT)
public class SporeDeityPowerLayer extends EnergySwirlLayer<SporeDeityEntity, SporeDeityModel<SporeDeityEntity>> {

    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("bedres:textures/entity/aura1.png");
    private final SporeDeityModel<SporeDeityEntity> model;

    public SporeDeityPowerLayer(RenderLayerParent<SporeDeityEntity, SporeDeityModel<SporeDeityEntity>> p_174475_, EntityModelSet p_174555_) {
        super(p_174475_);
        this.model = new SporeDeityModel<>(p_174555_.bakeLayer(ARMOR_LAYER_LOCATION));
    }

    protected float xOffset(float p_116683_) {
        return p_116683_ * 0.01F;
    }

    protected EntityModel<SporeDeityEntity> model() {
        return this.model;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return TEXTURE_ELYTRA;
    }
}
