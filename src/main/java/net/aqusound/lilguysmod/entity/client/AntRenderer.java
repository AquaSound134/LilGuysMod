package net.aqusound.lilguysmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.custom.AntEntity;
import net.aqusound.lilguysmod.entity.custom.WormEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AntRenderer extends MobRenderer<AntEntity, Little_Guy_Ant<AntEntity>> {
    public AntRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Little_Guy_Ant<>(pContext.bakeLayer(ModModelLayers.ANT_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(AntEntity pEntity) {
        return new ResourceLocation(LilGuysMod.MODID, "textures/entity/ant.png");
    }

    @Override
    public void render(AntEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

        if(pEntity.isBaby()){

            pPoseStack.scale(0.75f, 0.75f,0.75f);

        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
