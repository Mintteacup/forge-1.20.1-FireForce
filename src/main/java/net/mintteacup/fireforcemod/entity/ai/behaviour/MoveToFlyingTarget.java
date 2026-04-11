package net.mintteacup.fireforcemod.entity.ai.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.pathfinder.Path;
import net.mintteacup.fireforcemod.entity.custom.AdollaBugEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import javax.annotation.Nullable;

import java.util.List;

public class MoveToFlyingTarget extends ExtendedBehaviour<AdollaBugEntity> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED),
            Pair.of(MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT)
    );

    @Nullable
    protected Path path;
    @Nullable
    protected BlockPos lastTargetPos;
    protected float speedModifier;

    public MoveToFlyingTarget() {
        runFor(entity -> entity.getRandom().nextInt(100) + 150);
        cooldownFor(entity -> entity.getRandom().nextInt(40));
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, AdollaBugEntity entity) {
        WalkTarget walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);
        System.out.println("MoveToFlyingTarget checking conditions");
        if (!hasReachedTarget(entity, walkTarget) && attemptNewPath(entity, walkTarget, false)) {
            this.lastTargetPos = walkTarget.getTarget().currentBlockPosition();
            System.out.println("Path created, moving to target");
            return true;

        }

        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        BrainUtils.clearMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);

        return false;
    }

    @Override
    protected boolean shouldKeepRunning(AdollaBugEntity entity) {
        if (this.path == null || this.lastTargetPos == null)
            return false;

        if (entity.getNavigation().isDone())
            return false;

        WalkTarget walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);

        return walkTarget != null && !hasReachedTarget(entity, walkTarget);
    }

    @Override
    protected void start(AdollaBugEntity entity) {
        startOnNewPath(entity);
    }

    @Override
    protected void tick(AdollaBugEntity entity) {
        Path currentPath = entity.getNavigation().getPath();

        if (this.path != currentPath) {
            this.path = currentPath;
            BrainUtils.setMemory(entity, MemoryModuleType.PATH, currentPath);
        }

        if (currentPath != null && this.lastTargetPos != null) {
            WalkTarget walkTarget = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET);

            if (walkTarget.getTarget().currentBlockPosition().distSqr(this.lastTargetPos) > 4 &&
                    attemptNewPath(entity, walkTarget, hasReachedTarget(entity, walkTarget))) {
                this.lastTargetPos = walkTarget.getTarget().currentBlockPosition();
                startOnNewPath(entity);
            }
        }
    }

    @Override
    protected void stop(AdollaBugEntity entity) {
        if (!entity.getNavigation().isStuck() ||
                !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET) ||
                hasReachedTarget(entity, BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET))) {
            this.cooldownFinishedAt = 0;
        }

        entity.getNavigation().stop();
        BrainUtils.clearMemories(entity, MemoryModuleType.WALK_TARGET, MemoryModuleType.PATH);

        this.path = null;
    }

    protected boolean attemptNewPath(AdollaBugEntity entity, WalkTarget walkTarget, boolean reachedCurrentTarget) {
        BlockPos targetPos = walkTarget.getTarget().currentBlockPosition();
        this.path = entity.getNavigation().createPath(targetPos, 0);
        this.speedModifier = walkTarget.getSpeedModifier();

        if (reachedCurrentTarget) {
            BrainUtils.clearMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            return false;
        }

        if (this.path != null && this.path.canReach()) {
            BrainUtils.clearMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        } else {
            BrainUtils.setMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, entity.level().getGameTime());
        }

        System.out.println("Attempting new path to: " + targetPos);
        System.out.println("Path created: " + (this.path != null));
        System.out.println("Path can reach: " + (this.path != null && this.path.canReach()));
        return this.path != null;
    }

    protected boolean hasReachedTarget(AdollaBugEntity entity, WalkTarget target) {
        return target.getTarget().currentBlockPosition().distManhattan(entity.blockPosition()) <= target.getCloseEnoughDist();
    }

    protected void startOnNewPath(AdollaBugEntity entity) {
        BrainUtils.setMemory(entity, MemoryModuleType.PATH, this.path);
        entity.getNavigation().moveTo(this.path, this.speedModifier);
    }
}