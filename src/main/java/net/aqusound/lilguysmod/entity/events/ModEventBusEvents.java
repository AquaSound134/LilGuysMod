package net.aqusound.lilguysmod.entity.events;


import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.entity.custom.AntEntity;
import net.aqusound.lilguysmod.entity.custom.FireflyEntity;
import net.aqusound.lilguysmod.entity.custom.LizardEntity;
import net.aqusound.lilguysmod.entity.custom.WormEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LilGuysMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.Worm.get(), WormEntity.createAttributes().build());
        event.put(ModEntities.Ant.get(), AntEntity.createAttributes().build());
        event.put(ModEntities.Lizard.get(), LizardEntity.createAttributes().build());
        event.put(ModEntities.Firefly.get(), FireflyEntity.createAttributes().build());


    }




}
