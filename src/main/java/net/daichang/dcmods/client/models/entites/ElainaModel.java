package net.daichang.dcmods.client.models.entites;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.daichang.dcmods.client.animation.ElainaAnimation;
import net.daichang.dcmods.common.entities.boss.DCLoveElaina;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ElainaModel<T extends DCLoveElaina> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "elaina_model"), "main");
	private final ModelPart bone;
	private final ModelPart Head;
	private final ModelPart hat;
	private final ModelPart Body;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;

	public ElainaModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.Head = this.bone.getChild("Head");
		this.hat = this.Head.getChild("hat");
		this.Body = this.bone.getChild("Body");
		this.RightArm = this.bone.getChild("RightArm");
		this.LeftArm = this.bone.getChild("LeftArm");
		this.RightLeg = this.bone.getChild("RightLeg");
		this.LeftLeg = this.bone.getChild("LeftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Head = bone.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = Head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 109).addBox(-9.0F, -1.0F, -9.0F, 18.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 97).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 69).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.1309F, 0.0F, 0.1309F));

		PartDefinition cube_r1 = hat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(2, 80).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -2.5F, 7.0F, 0.8782F, 0.0924F, -0.7811F));

		PartDefinition cube_r2 = hat.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 82).addBox(-1.0F, -2.5F, -2.5F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -5.1F, 2.5F, -0.1719F, -0.7703F, 0.2444F));

		PartDefinition cube_r3 = hat.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 80).addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.0F, 8.0F, 0.1309F, 0.0F, -0.1309F));

		PartDefinition cube_r4 = hat.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 88).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.8F, 0.0F, -0.096F, 0.0F, 0.0F));

		PartDefinition Body = bone.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(32, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(24, 89).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(32, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(48, 89).addBox(-5.0F, 13.0F, -2.0F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(48, 78).addBox(-5.0F, 13.0F, -2.0F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.48F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition RightArm = bone.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(56, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(56, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(54, 110).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.8F))
		.texOffs(54, 121).addBox(-2.5F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.9F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition LeftArm = bone.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(54, 110).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.8F)).mirror(false)
		.texOffs(54, 121).mirror().addBox(-1.5F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.9F)).mirror(false), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition RightLeg = bone.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition LeftLeg = bone.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(32, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(DCLoveElaina boss, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (boss.isAlive()) {
			this.animate(boss.attackAnimationState, ElainaAnimation.step, ageInTicks);
			this.animate(boss.attackAnimationState_1, ElainaAnimation.attack_1, ageInTicks);
			this.animate(boss.attackAnimationState_2, ElainaAnimation.attack_2, ageInTicks);
			this.animate(boss.attackAnimationState_3, ElainaAnimation.attack_3, ageInTicks);
			this.animate(boss.walkAnimationState, ElainaAnimation.walk, ageInTicks);
			this.animate(boss.noGroundAnimationState, ElainaAnimation.no_ground, ageInTicks);
			this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
			this.Head.xRot = headPitch / (180F / (float) Math.PI);
		}
		this.animate(boss.isDeadAnimationState, ElainaAnimation.dead, ageInTicks);
		this.animate(boss.idleAnimationState, ElainaAnimation.idle, ageInTicks);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return bone;
	}
}