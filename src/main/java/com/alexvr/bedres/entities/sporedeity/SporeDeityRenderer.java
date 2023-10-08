package com.alexvr.bedres.entities.sporedeity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;

import javax.annotation.Nonnull;

public class SporeDeityRenderer extends LivingEntityRenderer<SporeDeityEntity, SporeDeityModel<SporeDeityEntity>> {

    public SporeDeityRenderer (EntityRendererProvider.Context context) {
        super(context,  new SporeDeityModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new SporeDeityPowerLayer(this, context.getModelSet()));
        this.addLayer(new ItemInHandLayer<>(this,context.getItemInHandRenderer()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(),context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));

    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(SporeDeityEntity pEntity) {
        Minecraft mc = Minecraft.getInstance();
        ResourceLocation texture = DefaultPlayerSkin.getDefaultSkin();

        if (mc.getCameraEntity() instanceof AbstractClientPlayer) {
            AbstractClientPlayer client = ((AbstractClientPlayer) mc.getCameraEntity());
            texture = client.getSkinTextureLocation();
        }

        return texture;
    }

    public void render(SporeDeityEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.setModelProperties(pEntity);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre(pEntity, this, pPartialTicks, pMatrixStack, pBuffer, pPackedLight))) return;
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post(pEntity, this, pPartialTicks, pMatrixStack, pBuffer, pPackedLight));
    }

    private void setModelProperties(SporeDeityEntity pClientPlayer) {
        SporeDeityModel<SporeDeityEntity> sporeDeityModel = this.getModel();
        if (pClientPlayer.isSpectator()) {
            sporeDeityModel.setAllVisible(false);
            sporeDeityModel.head.visible = true;
            sporeDeityModel.hat.visible = true;
        } else {
            sporeDeityModel.setAllVisible(true);
//            sporeDeityModel.hat.visible = pClientPlayer.isModelPartShown(PlayerModelPart.HAT);
//            sporeDeityModel.jacket.visible = pClientPlayer.isModelPartShown(PlayerModelPart.JACKET);
//            sporeDeityModel.leftPants.visible = pClientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
//            sporeDeityModel.rightPants.visible = pClientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
//            sporeDeityModel.leftSleeve.visible = pClientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
//            sporeDeityModel.rightSleeve.visible = pClientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
            sporeDeityModel.crouching = pClientPlayer.isCrouching();
            HumanoidModel.ArmPose humanoidmodel$armpose = getArmPose(pClientPlayer, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose humanoidmodel$armpose1 = getArmPose(pClientPlayer, InteractionHand.OFF_HAND);
            if (humanoidmodel$armpose.isTwoHanded()) {
                humanoidmodel$armpose1 = pClientPlayer.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }

            if (pClientPlayer.getMainArm() == HumanoidArm.RIGHT) {
                sporeDeityModel.rightArmPose = humanoidmodel$armpose;
                sporeDeityModel.leftArmPose = humanoidmodel$armpose1;
            } else {
                sporeDeityModel.rightArmPose = humanoidmodel$armpose1;
                sporeDeityModel.leftArmPose = humanoidmodel$armpose;
            }
        }

    }

    private static HumanoidModel.ArmPose getArmPose(Mob p_117795_, InteractionHand p_117796_) {
        ItemStack itemstack = p_117795_.getItemInHand(p_117796_);
        if (itemstack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (p_117795_.getUsedItemHand() == p_117796_ && p_117795_.getUseItemRemainingTicks() > 0) {
                UseAnim useanim = itemstack.getUseAnimation();
                if (useanim == UseAnim.BLOCK) {
                    return HumanoidModel.ArmPose.BLOCK;
                }

                if (useanim == UseAnim.BOW) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                if (useanim == UseAnim.SPEAR) {
                    return HumanoidModel.ArmPose.THROW_SPEAR;
                }

                if (useanim == UseAnim.CROSSBOW && p_117796_ == p_117795_.getUsedItemHand()) {
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useanim == UseAnim.SPYGLASS) {
                    return HumanoidModel.ArmPose.SPYGLASS;
                }
            } else if (!p_117795_.swinging && itemstack.is(Items.CROSSBOW) && CrossbowItem.isCharged(itemstack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            return HumanoidModel.ArmPose.ITEM;
        }
    }

    protected void scale(SporeDeityEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        float f = 0.9375F;
        pMatrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

}
