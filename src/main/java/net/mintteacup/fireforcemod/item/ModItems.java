package net.mintteacup.fireforcemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mintteacup.fireforcemod.FireForceMod;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FireForceMod.MODID);

    public static final RegistryObject<Item> ADOLLA_BUG = ITEMS.register("adolla_bug",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FIRE_EXTINGUISHER = ITEMS.register("fire_extinguisher",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
