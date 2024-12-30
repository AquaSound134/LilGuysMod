package net.aqusound.lilguysmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.custom.WormEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WormRenderer extends MobRenderer<WormEntity, Lil_Guy_Worm<WormEntity>> {
    public WormRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Lil_Guy_Worm<>(pContext.bakeLayer(ModModelLayers.WORM_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(WormEntity wormEntity) {
        return new ResourceLocation(LilGuysMod.MODID, "textures/entity/worm.png");
    }

    @Override
    public void render(WormEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

        if(pEntity.isBaby()){

            pPoseStack.scale(0.5f, 0.5f,0.5f);

        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
