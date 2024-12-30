package net.aqusound.lilguysmod;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.entity.client.AntRenderer;
import net.aqusound.lilguysmod.entity.client.FireflyRenderer;
import net.aqusound.lilguysmod.entity.client.LizardRender;
import net.aqusound.lilguysmod.entity.client.WormRenderer;
import net.aqusound.lilguysmod.entity.custom.AntEntity;
import net.aqusound.lilguysmod.entity.custom.BottleOfWormThrowableEntity;
import net.aqusound.lilguysmod.entity.custom.WormEntity;
import net.aqusound.lilguysmod.item.ModCreativeModeTabs;
import net.aqusound.lilguysmod.item.ModItems;


import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static net.aqusound.lilguysmod.entity.ModEntities.*;
import static net.minecraft.world.entity.SpawnPlacements.Type.ON_GROUND;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LilGuysMod.MODID)
public class LilGuysMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "lilguysmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();



    
    public LilGuysMod(FMLJavaModLoadingContext context)
    {

        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        ModEntities.register(modEventBus);



        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



        private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void blockBreakEvent(BlockEvent.BreakEvent e){

        System.out.println("any block");

        Level level = (Level) e.getLevel();
        if(e.getState().getBlock() == Blocks.DIRT){
            System.out.println("dirt");


            EntityType<WormEntity> entityType = Worm.get();

            //1 in 20 chance
            if((int)(Math.random() * 64) == 0){
                entityType.spawn((ServerLevel) level, e.getPos(), MobSpawnType.NATURAL);
            }

        }

    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(Worm.get(), WormRenderer::new);
            //System.out.println("make ant");
            EntityRenderers.register(Ant.get(), AntRenderer::new);
           // System.out.println("ant made");
            EntityRenderers.register(Lizard.get(), LizardRender::new);

            EntityRenderers.register(Firefly.get(), FireflyRenderer::new);

            EntityRenderers.register(BottleOfWormEntity.get(), ThrownItemRenderer::new);
        }
    }
}
