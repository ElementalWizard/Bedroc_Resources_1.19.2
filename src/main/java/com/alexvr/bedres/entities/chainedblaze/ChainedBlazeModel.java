package com.alexvr.bedres.entities.chainedblaze;// Made with Blockbench 4.1.3
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ChainedBlazeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BedrockResources.MODID, "chained_blaze"), "main");
	private final ModelPart chain;
	private final ModelPart chain2;
	private final ModelPart chain3;
	private final ModelPart chain4;
	private final ModelPart chain5;
	private final ModelPart chain6;
	private final ModelPart chain7;
	private final ModelPart chain8;
	private final ModelPart head;
	private final ModelPart[] upperBodyParts;

	public ChainedBlazeModel(ModelPart root) {
		this.chain = root.getChild("chain");
		this.chain2 = root.getChild("chain2");
		this.chain3 = root.getChild("chain3");
		this.chain4 = root.getChild("chain4");
		this.chain5 = root.getChild("chain5");
		this.chain6 = root.getChild("chain6");
		this.chain7 = root.getChild("chain7");
		this.chain8 = root.getChild("chain8");
		this.head = root.getChild("bb_main");
		upperBodyParts = new ModelPart[9];
		upperBodyParts[0] = chain;
		upperBodyParts[1] = chain2;
		upperBodyParts[2] = chain3;
		upperBodyParts[3] = chain4;
		upperBodyParts[4] = chain5;
		upperBodyParts[5] = chain6;
		upperBodyParts[6] = chain7;
		upperBodyParts[7] = chain8;
	}
	private static String getPartName(int pIndex) {
		return "chain" + pIndex;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition chain = partdefinition.addOrReplaceChild("chain", CubeListBuilder.create(), PartPose.offset(-4.0F, 9.0F, 7.0F));

		PartDefinition latch = chain.addOrReplaceChild("latch", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 15.0F, -2.0F));

		PartDefinition latch2 = chain.addOrReplaceChild("latch2", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition latch3 = chain.addOrReplaceChild("latch3", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 5.0F, -2.0F));

		PartDefinition latch4 = chain.addOrReplaceChild("latch4", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -11.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -17.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain2 = partdefinition.addOrReplaceChild("chain2", CubeListBuilder.create(), PartPose.offset(6.0F, 9.0F, 7.0F));

		PartDefinition latch5 = chain2.addOrReplaceChild("latch5", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 15.0F, -2.0F));

		PartDefinition latch6 = chain2.addOrReplaceChild("latch6", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition latch7 = chain2.addOrReplaceChild("latch7", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 5.0F, -2.0F));

		PartDefinition latch8 = chain2.addOrReplaceChild("latch8", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -11.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -17.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain3 = partdefinition.addOrReplaceChild("chain3", CubeListBuilder.create(), PartPose.offset(-4.0F, 9.0F, -3.0F));

		PartDefinition latch9 = chain3.addOrReplaceChild("latch9", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 15.0F, -2.0F));

		PartDefinition latch10 = chain3.addOrReplaceChild("latch10", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition latch11 = chain3.addOrReplaceChild("latch11", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 5.0F, -2.0F));

		PartDefinition latch12 = chain3.addOrReplaceChild("latch12", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -11.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -17.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain4 = partdefinition.addOrReplaceChild("chain4", CubeListBuilder.create(), PartPose.offset(6.0F, 9.0F, -3.0F));

		PartDefinition latch13 = chain4.addOrReplaceChild("latch13", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 15.0F, -2.0F));

		PartDefinition latch14 = chain4.addOrReplaceChild("latch14", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition latch15 = chain4.addOrReplaceChild("latch15", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -6.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 5.0F, -2.0F));

		PartDefinition latch16 = chain4.addOrReplaceChild("latch16", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -11.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, -17.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain5 = partdefinition.addOrReplaceChild("chain5", CubeListBuilder.create(), PartPose.offset(1.0F, -5.0F, -3.0F));

		PartDefinition latch17 = chain5.addOrReplaceChild("latch17", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 12.0F, -2.0F));

		PartDefinition latch18 = chain5.addOrReplaceChild("latch18", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain6 = partdefinition.addOrReplaceChild("chain6", CubeListBuilder.create(), PartPose.offset(1.0F, -5.0F, 7.0F));

		PartDefinition latch19 = chain6.addOrReplaceChild("latch19", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 12.0F, -2.0F));

		PartDefinition latch20 = chain6.addOrReplaceChild("latch20", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain7 = partdefinition.addOrReplaceChild("chain7", CubeListBuilder.create(), PartPose.offset(6.0F, -5.0F, 2.0F));

		PartDefinition latch21 = chain7.addOrReplaceChild("latch21", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 12.0F, -2.0F));

		PartDefinition latch22 = chain7.addOrReplaceChild("latch22", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition chain8 = partdefinition.addOrReplaceChild("chain8", CubeListBuilder.create(), PartPose.offset(-4.0F, -5.0F, 2.0F));

		PartDefinition latch23 = chain8.addOrReplaceChild("latch23", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 12.0F, -2.0F));

		PartDefinition latch24 = chain8.addOrReplaceChild("latch24", CubeListBuilder.create().texOffs(16, 1).addBox(-4.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 7.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(-3.0F, 3.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 1).addBox(0.0F, 4.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, -3.1416F, -1.5708F, 3.1416F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -24.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
	private float amountMoved = 0;
	private int movingDirection = 1;
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		float f = pAgeInTicks * ((float)Math.PI/2) * -0.1F;

		for(int i = 0; i < 4; ++i) {
			this.upperBodyParts[i].y = 10.0F + Mth.cos(((float)(i * 16) + pAgeInTicks) * 0.50F);
			this.upperBodyParts[i].x = Mth.cos(f) * 9.0F;
			this.upperBodyParts[i].z = Mth.sin(f) * 9.0F;
			++f;
		}

		f = ((float)Math.PI / 4F) + pAgeInTicks * (float)Math.PI * 0.03F;

		for(int j = 4; j < 8; ++j) {
			this.upperBodyParts[j].y = -5.0F + Mth.cos(((float)(j * 2) + pAgeInTicks) * 0.50F);
			this.upperBodyParts[j].x = Mth.cos(f) * 7.0F;
			this.upperBodyParts[j].z = Mth.sin(f) * 7.0F;
			++f;
		}

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		chain.render(poseStack, buffer, packedLight, packedOverlay);
		chain2.render(poseStack, buffer, packedLight, packedOverlay);
		chain3.render(poseStack, buffer, packedLight, packedOverlay);
		chain4.render(poseStack, buffer, packedLight, packedOverlay);
		chain5.render(poseStack, buffer, packedLight, packedOverlay);
		chain6.render(poseStack, buffer, packedLight, packedOverlay);
		chain7.render(poseStack, buffer, packedLight, packedOverlay);
		chain8.render(poseStack, buffer, packedLight, packedOverlay);
		head.render(poseStack, buffer, packedLight, packedOverlay);
	}
}