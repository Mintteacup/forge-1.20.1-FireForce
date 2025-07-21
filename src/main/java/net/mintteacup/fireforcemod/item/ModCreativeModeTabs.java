package net.mintteacup.fireforcemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.block.ModBlocks;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FireForceMod.MODID);

    public static final RegistryObject<CreativeModeTab> FIREFORCE_TAB =
            CREATIVE_MODE_TABS.register("fireforce_tab",
                    () -> CreativeModeTab.builder().icon( () -> new ItemStack(ModItems.ADOLLA_BUG.get()))
                            .title(Component.translatable("creativetab.fireforce_tab"))
                            .displayItems((itemDisplayParameters, output) -> {
                                output.accept(ModItems.ADOLLA_BUG.get());
                                output.accept(ModItems.FIRE_EXTINGUISHER.get());

                                output.accept(ModBlocks.HOLY_SOL_TEMPLE_BLOCK.get());
                            })
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
