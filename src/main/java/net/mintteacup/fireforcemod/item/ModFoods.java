package net.mintteacup.fireforcemod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties ADOLLA_BUG = new FoodProperties.Builder().nutrition(2).alwaysEat()
            .saturationMod(0.2F).effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 100), 1).build();
}
