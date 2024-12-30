package net.aqusound.lilguysmod.item;


import net.aqusound.lilguysmod.LilGuysMod;
import net.aqusound.lilguysmod.entity.ModEntities;
import net.aqusound.lilguysmod.entity.custom.WormEntity;
import net.aqusound.lilguysmod.item.custom.bottleoffireflyitem;
import net.aqusound.lilguysmod.item.custom.bottleofwormitem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LilGuysMod.MODID);

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOTTLE_OF_WORM = ITEMS.register("bottleofworm",
            ()-> new bottleofwormitem(new Item.Properties()));
    public static final RegistryObject<Item> BOTTLE_OF_FIREFLY = ITEMS.register("bottleoffirefly",
            ()-> new bottleoffireflyitem(new Item.Properties()));
    public static final RegistryObject<Item> WORM_SPAWN_EGG = ITEMS.register("wormspawnegg",
            () -> new ForgeSpawnEggItem(ModEntities.Worm,0xffdbf8, 0xffaff0, new Item.Properties()));
    public static final RegistryObject<Item> ANT_SPAWN_EGG = ITEMS.register("antspawnegg",
            () -> new ForgeSpawnEggItem(ModEntities.Ant,0x525252, 0x261202, new Item.Properties()));
    public static final RegistryObject<Item> LIZARD_SPAWN_EGG = ITEMS.register("lizardspawnegg",
            () -> new ForgeSpawnEggItem(ModEntities.Lizard,0x00b309, 0x004a04, new Item.Properties()));
    public static final RegistryObject<Item> FIREFLY_SPAWN_EGG = ITEMS.register("fireflyspawnegg",
            () -> new ForgeSpawnEggItem(ModEntities.Firefly,0x12ff00, 0xffe100, new Item.Properties()));
    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);
    }

    //public static final RegistryObject<Item> WORM_SPAWN_EGG = ITEMS.register("worm_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.Worm,0xffdbf8, 0xffaff0, new Item.Properties()));
}
