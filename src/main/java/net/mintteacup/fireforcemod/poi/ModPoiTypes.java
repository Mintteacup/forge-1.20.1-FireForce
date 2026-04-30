package net.mintteacup.fireforcemod.poi;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.block.ModBlocks;

import java.util.Set;

public class ModPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, FireForceMod.MODID);

    public static final RegistryObject<PoiType> ADOLLA_NEST =
            POI_TYPES.register("adolla_nest",
                    () -> new PoiType(Set.of(ModBlocks.ADOLLA_NEST.get().defaultBlockState()), 0, 1));
}