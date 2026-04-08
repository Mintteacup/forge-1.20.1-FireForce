package net.mintteacup.fireforcemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.item.custom.FireExtinguisherItem;
import net.mintteacup.fireforcemod.item.custom.FoamBallItem;

public class ModItems {

    // Forge's system to safely register items/blocks
    // Creates a DeferredRegister to hold items
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FireForceMod.MODID);

    public static final RegistryObject<Item> ADOLLA_BUG = ITEMS.register("adolla_bug",
            () -> new Item(new Item.Properties().food(ModFoods.ADOLLA_BUG)));

    public static final RegistryObject<Item> FIRE_EXTINGUISHER = ITEMS.register("fire_extinguisher",
            () -> new FireExtinguisherItem(new Item.Properties()));

    public static final RegistryObject<Item> FOAM_BALL = ITEMS.register("foam_ball",
            () -> new FoamBallItem(new Item.Properties()));

    public static final RegistryObject<Item> CATACLYSMITE_CHUNK = ITEMS.register("cataclysmite_chunk",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
