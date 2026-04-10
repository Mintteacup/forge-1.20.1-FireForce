package net.mintteacup.fireforcemod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FireForceMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CATACLYSMITE_ORE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.CATACLYSMITE_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.FOAM_BLOCK.get());
    }
}
