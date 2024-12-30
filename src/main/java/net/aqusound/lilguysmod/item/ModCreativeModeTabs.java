package net.aqusound.lilguysmod.item;

import net.aqusound.lilguysmod.LilGuysMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LilGuysMod.MODID);


    public static RegistryObject<CreativeModeTab> LIL_GUYS_TAB = CREATIVE_MODE_TABS.register("lil_guys_tab",
            ()-> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WORM_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.lil_guys_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ANT_SPAWN_EGG.get());
                        output.accept(ModItems.WORM_SPAWN_EGG.get());
                        output.accept(ModItems.LIZARD_SPAWN_EGG.get());
                        output.accept(ModItems.FIREFLY_SPAWN_EGG.get());
                        output.accept(ModItems.BOTTLE_OF_WORM.get());
                        output.accept(ModItems.BOTTLE_OF_FIREFLY.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){

        CREATIVE_MODE_TABS.register(eventBus);

    }
}
