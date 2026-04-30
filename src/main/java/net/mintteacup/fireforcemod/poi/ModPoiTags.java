package net.mintteacup.fireforcemod.poi;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.mintteacup.fireforcemod.FireForceMod;

public class ModPoiTags {
    public static final TagKey<PoiType> ADOLLA_HOME = TagKey.create(
            Registries.POINT_OF_INTEREST_TYPE,
            new ResourceLocation(FireForceMod.MODID, "adolla_home")
    );
}