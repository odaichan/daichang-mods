package net.daichang.dcmods.client.models.entites;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


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

public class ModelLoliEntity<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "loli"), "main");
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart LeftLeg;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart RightLeg;

	public ModelLoliEntity(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Head = root.getChild("Head");
		this.LeftLeg = root.getChild("LeftLeg");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		this.RightLeg = root.getChild("RightLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(32, 20).addBox(-3.0F, -15.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(18, 43).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.25F))
		.texOffs(32, 12).addBox(-3.2F, -8.0F, -3.1F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(32, 31).addBox(3.2F, -8.0F, -4.1F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(50, 35).addBox(-4.2F, -1.2F, 1.9F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(50, 31).addBox(-4.2F, -2.2F, 0.9F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(46, 43).addBox(-4.2F, -3.2F, -1.1F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(40, 0).addBox(-4.2F, -4.0F, -3.1F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(32, 31).addBox(-4.2F, -8.0F, -4.1F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(40, 0).addBox(3.2F, -4.0F, -3.1F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(46, 43).addBox(3.2F, -3.0F, -1.1F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(50, 31).addBox(3.2F, -2.0F, 0.9F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(50, 35).addBox(3.2F, -1.0F, 1.9F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 8).addBox(-4.0F, -8.0F, -4.1F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 38).addBox(-4.0F, -6.0F, -4.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 38).addBox(-1.0F, -6.0F, -4.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 38).addBox(2.0F, -6.0F, -4.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 54).addBox(-3.9F, -5.0F, -4.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 54).addBox(0.0F, -5.0F, -4.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 54).addBox(3.0F, -5.0F, -4.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(-3.8F, -8.0F, 3.1F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 38).addBox(-2.8F, 2.0F, 3.1F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 41).addBox(-1.8F, 4.0F, 3.6F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 41).addBox(-2.3F, 5.0F, 4.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 41).addBox(-1.3F, 6.0F, 4.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 41).addBox(-1.8F, 5.0F, 4.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 40).addBox(-0.8F, 6.0F, 4.6F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 41).addBox(-1.3F, 7.0F, 5.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 40).addBox(-0.8F, 7.0F, 5.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 41).addBox(-2.3F, 4.0F, 3.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 49).addBox(-4.2F, -8.0F, 3.1F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 54).addBox(-3.2F, 2.0F, 3.1F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(34, 43).addBox(-2.0F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 18.0F, 0.0F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 49).addBox(-1.6486F, 0.4074F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 50).addBox(-1.1486F, 3.4074F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.75F, 8.5F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(46, 49).addBox(-1.1014F, 0.4074F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 50).addBox(-0.6014F, 3.4074F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 8.5F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(34, 43).addBox(-2.0F, -2.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
						  float headPitch) {
		this.LeftLeg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.RightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.Head.xRot = headPitch / (180F / (float) Math.PI);
		this.RightLeg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.LeftArm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}