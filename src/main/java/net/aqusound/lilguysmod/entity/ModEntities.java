package net.aqusound.lilguysmod.entity;

import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LilGuysMod.MODID);


    public static final RegistryObject<EntityType<WormEntity>> Worm =
            ENTITY_TYPES.register("worm", () -> EntityType.Builder.of(WormEntity :: new, MobCategory.CREATURE)
                    .sized(0.5f, 0.2f).build("worm"));

    public static final RegistryObject<EntityType<AntEntity>> Ant =
            ENTITY_TYPES.register("ant", () -> EntityType.Builder.of(AntEntity :: new, MobCategory.CREATURE)
                    .sized(0.6f, 0.6f).build("ant"));

    public static final RegistryObject<EntityType<LizardEntity>> Lizard =
            ENTITY_TYPES.register("lizard", () -> EntityType.Builder.of(LizardEntity :: new, MobCategory.CREATURE)
                    .sized(0.8f, 0.5f).build("lizard"));

    public static final RegistryObject<EntityType<FireflyEntity>> Firefly =
            ENTITY_TYPES.register("firefly", () -> EntityType.Builder.of(FireflyEntity :: new, MobCategory.CREATURE)
                    .sized(0.3f, 1.1f).build("firefly"));

    public static final RegistryObject<EntityType<BottleOfWormThrowableEntity>> BottleOfWormEntity =
            ENTITY_TYPES.register("bottleofwormentity", () -> EntityType.Builder.<BottleOfWormThrowableEntity>of(BottleOfWormThrowableEntity:: new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("bottleofwormentity"));

    


    public static void register(IEventBus eventBus){

        ENTITY_TYPES.register(eventBus);

    }
}
