package net.aqusound.lilguysmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.custom.AntEntity;
import net.aqusound.lilguysmod.entity.custom.FireflyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

  public class FireflyRenderer extends MobRenderer<FireflyEntity, Lil_Guy_Firefly<FireflyEntity>> {
        public FireflyRenderer(EntityRendererProvider.Context pContext) {
            super(pContext, new Lil_Guy_Firefly<>(pContext.bakeLayer(ModModelLayers.FIREFLY_LAYER)), 0.1f);
        }


      @Override
        public ResourceLocation getTextureLocation(FireflyEntity pEntity) {
            return new ResourceLocation(LilGuysMod.MODID, "textures/entity/firefly.png");
        }



      @Override
        public void render(FireflyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

            if(pEntity.isBaby()){

                pPoseStack.scale(0.75f, 0.75f,0.75f);

            }

            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }


