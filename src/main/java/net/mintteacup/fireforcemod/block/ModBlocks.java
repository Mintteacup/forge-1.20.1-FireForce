package net.mintteacup.fireforcemod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    // Forge's system to safely register items/blocks
    // Creates a DeferredRegister for Block objects
    // ForgeRegistries.BLOCKS specifies we're registering blocks
    // FireForceMod.MODID uses my mod's ID as the namespace
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FireForceMod.MODID);

    // Registers the Holy Sol Temple block
    // RegistryObject holds a reference to the registered block
    // "holy_sol_temple_block" is the registry name (ID)
    // Uses properties copied from coal block (similar characteristics)
    public static final RegistryObject<Block> HOLY_SOL_TEMPLE_BLOCK = registerBlock("holy_sol_temple_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> CATACLYSMITE_ORE = registerBlock("cataclysmite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));


    // Helper method to register a block and its corresponding item
    // <T extends Block> makes this work with any Block subclass
    // Takes a registry name and a Supplier that creates the block
    // Returns a RegistryObject holding the registered block
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Helper method to register a BlockItem for a block
    // Creates an item form of the block that can be placed in inventory
    // Links to the same registry name as the block
    // Returns RegistryObject for the created BlockItem
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Registers all blocks with the mod event bus
    // Called during mod construction
    // eventBus is provided by Forge's mod loading system
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
