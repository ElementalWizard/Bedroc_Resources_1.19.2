package com.alexvr.bedres.entities.fluxedcreep;//Made with Blockbench
//Paste this code into your mod.


import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.BedrockReferences;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class FluxedCreepModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart bone;

	public static ModelLayerLocation FLUXED_CREEP_LAYER = new ModelLayerLocation(new ResourceLocation(BedrockResources.MODID, BedrockReferences.FLUXED_CREEP_REGNAME), "main");
	public static ModelLayerLocation FLUXED_CREEP_OUTER_LAYER = new ModelLayerLocation(new ResourceLocation(BedrockResources.MODID, BedrockReferences.FLUXED_CREEP_REGNAME), "outer");

	public FluxedCreepModel(ModelPart pRoot) {
		this.bone = pRoot;

	}

	public static LayerDefinition createOuterBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public static LayerDefinition createInnerBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 64).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 4.0F, 4.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 128, 128);
	}
	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
		bone.render(pPoseStack,pBuffer,pPackedLight,pPackedOverlay);
	}

	@Override
	public ModelPart root() {
		return this.bone;
	}


	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

	}
}