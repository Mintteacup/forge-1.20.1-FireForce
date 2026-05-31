package net.mintteacup.fireforcemod.abilitysystem.core;

import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {

    private static final Map<String, IAbility> abilities = new HashMap<>();

    private AbilityRegistry() {
    }

    public static void register(String id, IAbility ability) {
        abilities.put(id, ability);
    }

    public static IAbility get(String id) {
        return abilities.get(id);
    }

}

