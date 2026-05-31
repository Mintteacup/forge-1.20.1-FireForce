package net.mintteacup.fireforcemod.abilitysystem;

import net.mintteacup.fireforcemod.abilitysystem.abilities.utility.SelfHealAbility;
import net.mintteacup.fireforcemod.abilitysystem.core.AbilityRegistry;

public class ModAbilities {

    public static void register() {

        AbilityRegistry.register("self_heal", new SelfHealAbility());
    }

}
