package net.mintteacup.fireforcemod.entity.ai.behaviour;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.mintteacup.fireforcemod.entity.custom.AdollaBugEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetRandomFlyingTarget extends ExtendedBehaviour<AdollaBugEntity> {

    // Memory requirement: Only run if no WALK_TARGET exists (avoids interrupting current movement)
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            List.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));

    // Default movement speed multiplier
    protected float speedModifier = 1.0f;

    protected SquareRadius radius = new SquareRadius(8, 4);

    // Setter method for speed modifier
    public SetRandomFlyingTarget speedModifier(float speed) {
        this.speedModifier = speed;
        return this;
    }

    public SetRandomFlyingTarget setRadius(double xz, double y) {
        this.radius = new SquareRadius(xz, y);
        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    // Called when the behaviour starts
    @Override
    protected void start(AdollaBugEntity entity) {
        // Find a random valid flying position
        Vec3 target = getRandomFlyingPosition(entity);

        if (target != null) {
            // Wrap the position in a WalkTarget object (includes position, speed, and close-enough distance)
            WalkTarget walkTarget = new WalkTarget(target, this.speedModifier, 0);
            // Store it in the brain's memory for MoveToFlyingTarget to read
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, walkTarget);
            System.out.println("Setting random target");
        }
    }

    // Finds a random air position within range
    private Vec3 getRandomFlyingPosition(AdollaBugEntity entity) {

        System.out.println("Radius XZ: " + radius.xzRadius() + ", Y: " + radius.yRadius());

        // Try up to 10 times to find a valid position
        for (int i = 0; i < 10; i++) {
            // Random offsets based on radius values (XZ radius and Y radius)
            int xOffset = entity.getRandom().nextInt((int)(radius.xzRadius() * 2) + 1) - (int)radius.xzRadius();
            int zOffset = entity.getRandom().nextInt((int)(radius.xzRadius() * 2) + 1) - (int)radius.xzRadius();
            int yOffset = entity.getRandom().nextInt((int)(radius.yRadius() * 2) + 1) - (int)radius.yRadius();

            // Calculate target position using spawn position as center
            BlockPos targetPos = new BlockPos(
                    entity.getSpawnPos().getX() + xOffset,
                    entity.getSpawnPos().getY() + yOffset,
                    entity.getSpawnPos().getZ() + zOffset
            );

            System.out.println("xOffset: " + xOffset + ", yOffset: " + yOffset + ", zOffset: " + zOffset);

            // Check if target block is air AND block above is air (room to fly)
            if (entity.level().getBlockState(targetPos).isAir() && entity.level().getBlockState(targetPos.above()).isAir()) {
                System.out.println("Found target position: " + targetPos);
                // Convert to Vec3 (center of the block) and return
                return new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
            }
        }
        // No valid position found after 10 attempts
        return null;
    }
}