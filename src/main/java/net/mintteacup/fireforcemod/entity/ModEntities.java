package net.mintteacup.fireforcemod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mintteacup.fireforcemod.FireForceMod;
import net.mintteacup.fireforcemod.entity.custom.AdollaBugEntity;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FireForceMod.MODID);

    public static final RegistryObject<EntityType<AdollaBugEntity>> ADOLLA_BUG =
            ENTITIES.register("adolla_bug",
                    () -> EntityType.Builder.of(AdollaBugEntity::new, MobCategory.MONSTER)
                            .sized(0.2f, 0.2f)
                            .build("adolla_bug"));
}