package net.mintteacup.fireforcemod.abilitysystem.abilities.utility;

import net.minecraft.world.entity.LivingEntity;
import net.mintteacup.fireforcemod.abilitysystem.core.AbilityContext;
import net.mintteacup.fireforcemod.abilitysystem.core.IAbility;

public class SelfHealAbility implements IAbility {
    @Override
    public boolean canActivate(AbilityContext context) {

        LivingEntity owner = context.getOwner();

        return owner.isAlive();
    }

    @Override
    public void execute(AbilityContext context) {

        LivingEntity owner = context.getOwner();
        owner.heal(4.0f);
    }

    @Override
    public String getID() {
        return "self_heal";
    }
}
