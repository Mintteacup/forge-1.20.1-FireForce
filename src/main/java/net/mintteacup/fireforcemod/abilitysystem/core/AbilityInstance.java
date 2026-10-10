package net.mintteacup.fireforcemod.abilitysystem.core;

public class AbilityInstance {
    private final IAbility ability;
    private final int maxCooldown;
    private int currentCooldown;

    public AbilityInstance(IAbility ability, int maxCooldown) {
        this.ability = ability;
        this.maxCooldown = maxCooldown;
        this.currentCooldown = 0;
    }

    public boolean isReady() {
        return currentCooldown <= 0;
    }

    public void use() {
        currentCooldown = maxCooldown;
    }

    public void tick() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }
}
