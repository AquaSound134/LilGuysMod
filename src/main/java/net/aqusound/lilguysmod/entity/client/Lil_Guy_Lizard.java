package net.aqusound.lilguysmod.entity.client;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.aqusound.lilguysmod.entity.animations.AntAnimationsDefinitions;
import net.aqusound.lilguysmod.entity.animations.LizardAnimationDefinitions;
import net.aqusound.lilguysmod.entity.custom.AntEntity;
import net.aqusound.lilguysmod.entity.custom.LizardEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Lil_Guy_Lizard<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "lil_guy_lizard"), "main");
	private final ModelPart root;
	private final ModelPart legs;
	private final ModelPart rightfront;
	private final ModelPart rightback;
	private final ModelPart Leftfront;
	private final ModelPart leftback;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart Tail;
	private final ModelPart one;
	private final ModelPart two;
	private final ModelPart three;
	private final ModelPart four;

	public Lil_Guy_Lizard(ModelPart root) {
		this.root = root.getChild("root");
		this.legs = this.root.getChild("legs");
		this.rightfront = this.legs.getChild("rightfront");
		this.rightback = this.legs.getChild("rightback");
		this.Leftfront = this.legs.getChild("Leftfront");
		this.leftback = this.legs.getChild("leftback");
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.nose = this.head.getChild("nose");
		this.Tail = this.body.getChild("Tail");
		this.one = this.Tail.getChild("1");
		this.two = this.one.getChild("2");
		this.three = this.two.getChild("3");
		this.four = this.three.getChild("4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.5F, 0.0F));

		PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

		PartDefinition rightfront = legs.addOrReplaceChild("rightfront", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.15F, -0.5F, -1.5F));

		PartDefinition rightback = legs.addOrReplaceChild("rightback", CubeListBuilder.create().texOffs(12, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.15F, -0.5F, 1.25F));

		PartDefinition Leftfront = legs.addOrReplaceChild("Leftfront", CubeListBuilder.create().texOffs(4, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.15F, -0.5F, -1.5F));

		PartDefinition leftback = legs.addOrReplaceChild("leftback", CubeListBuilder.create().texOffs(8, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.15F, -0.5F, 1.25F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(2, 0).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, -0.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.5F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.5F));

		PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.25F, 3.0F));

		PartDefinition one = Tail.addOrReplaceChild("1", CubeListBuilder.create().texOffs(10, 10).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition two  = one.addOrReplaceChild("2", CubeListBuilder.create().texOffs(10, 7).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

		PartDefinition three = two.addOrReplaceChild("3", CubeListBuilder.create().texOffs(6, 11).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

		PartDefinition four = three.addOrReplaceChild("4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			this.root.getAllParts().forEach(ModelPart::resetPose);
			this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
			this.animateWalk(LizardAnimationDefinitions.LIZARDWALK, limbSwing,limbSwingAmount, 2f, 2.5f);
			this.animate(((LizardEntity) entity).idleAnimationState, LizardAnimationDefinitions.LIZARDIDLE, ageInTicks, 1f);
			this.animate(((LizardEntity) entity).tailAnimationState, LizardAnimationDefinitions.LIZARDTAILWAG, ageInTicks, 1f);
	}


	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -20.0F, 20.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

}