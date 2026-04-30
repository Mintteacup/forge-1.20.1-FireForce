package net.mintteacup.fireforcemod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.block.ModBlocks;
import net.mintteacup.fireforcemod.poi.ModPoiTags;
import net.mintteacup.fireforcemod.poi.ModPoiTypes;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {

    public ModPoiTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FireForceMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(ModPoiTags.ADOLLA_HOME)
                .add(ModPoiTypes.ADOLLA_NEST.getKey());
    }

}
