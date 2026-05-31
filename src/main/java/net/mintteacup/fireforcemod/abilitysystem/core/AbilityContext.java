package net.mintteacup.fireforcemod.abilitysystem.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AbilityContext {
    private final LivingEntity owner;
    private final Level world;
    private final Entity target;
    private final Vec3 targetPosition;
    private final String triggerType;

    public AbilityContext(LivingEntity owner, Level world, Entity target, Vec3 targetPosition, String triggerType) {
        this.owner = owner;
        this.world = world;
        this.target = target;
        this.targetPosition = targetPosition;
        this.triggerType = triggerType;
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    public Level getWorld() {
        return this.world;
    }

    public Entity getTarget() {
        return this.target;
    }

    public Vec3 getTargetPosition() {
        return this.targetPosition;
    }

    public String getTriggerType() {
        return this.triggerType;
    }

}


