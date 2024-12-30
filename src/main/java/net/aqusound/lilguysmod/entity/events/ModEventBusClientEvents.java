package net.aqusound.lilguysmod.entity.events;


import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LilGuysMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.WORM_LAYER, Lil_Guy_Worm::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ANT_LAYER, Little_Guy_Ant:: createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LIZARD_LAYER, Lil_Guy_Lizard:: createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FIREFLY_LAYER, Lil_Guy_Firefly:: createBodyLayer);
    }



}
