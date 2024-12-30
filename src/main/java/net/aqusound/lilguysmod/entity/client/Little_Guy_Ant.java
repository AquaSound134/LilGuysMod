package net.aqusound.lilguysmod.entity.client;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.aqusound.lilguysmod.entity.animations.AntAnimationsDefinitions;
import net.aqusound.lilguysmod.entity.animations.WormAnimationDefinitions;
import net.aqusound.lilguysmod.entity.custom.AntEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Little_Guy_Ant<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("lilguysmod", "little_guy_ant"), "main");
	private final ModelPart Root;
	private final ModelPart Legs;
	private final ModelPart RightLEgFront;
	private final ModelPart LeftLegFront;
	private final ModelPart LeftLegBack;
	private final ModelPart RightLegBack;
	private final ModelPart Body;
	private final ModelPart Neck;
	private final ModelPart Head;
	private final ModelPart Antena;

	public Little_Guy_Ant(ModelPart root) {
		this.Root = root.getChild("Root");
		this.Legs = this.Root.getChild("Legs");
		this.RightLEgFront = this.Legs.getChild("RightLEgFront");
		this.LeftLegFront = this.Legs.getChild("LeftLegFront");
		this.LeftLegBack = this.Legs.getChild("LeftLegBack");
		this.RightLegBack = this.Legs.getChild("RightLegBack");
		this.Body = this.Root.getChild("Body");
		this.Neck = this.Body.getChild("Neck");
		this.Head = this.Neck.getChild("Head");
		this.Antena = this.Head.getChild("Antena");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Legs = Root.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 0.0F));

		PartDefinition RightLEgFront = Legs.addOrReplaceChild("RightLEgFront", CubeListBuilder.create().texOffs(0, 0).addBox(-0.25F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

		PartDefinition LeftLegFront = Legs.addOrReplaceChild("LeftLegFront", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 0.0F));

		PartDefinition LeftLegBack = Legs.addOrReplaceChild("LeftLegBack", CubeListBuilder.create().texOffs(13, 0).addBox(-0.75F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, -1.0F, 2.0F));

		PartDefinition RightLegBack = Legs.addOrReplaceChild("RightLegBack", CubeListBuilder.create().texOffs(12, 12).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.75F, -1.0F, 2.0F));

		PartDefinition Body = Root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -2.5F, 4.0F, 3.5F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.5F));

		PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(12, 8).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -2.5F));

		PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.5F));

		PartDefinition Antena = Head.addOrReplaceChild("Antena", CubeListBuilder.create().texOffs(5, 19).addBox(0.5F, -2.75F, -2.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 17).addBox(0.5F, -2.45F, -1.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 19).addBox(0.5F, -2.05F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 17).addBox(-0.5F, -2.05F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 17).addBox(-0.5F, -2.45F, -1.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 16).addBox(-0.5F, -2.75F, -2.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.45F, -0.4F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		//System.out.println("walk animation set up");


		this.animateWalk(AntAnimationsDefinitions.LITTLE_GUY_ANT_WALK, limbSwing,limbSwingAmount, 2f, 2.5f);
		this.animate(((AntEntity) entity).idleAnimationState, AntAnimationsDefinitions.LITTLE_GUY_ANT_IDLE, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -20.0F, 20.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.Head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Root;
	}


}