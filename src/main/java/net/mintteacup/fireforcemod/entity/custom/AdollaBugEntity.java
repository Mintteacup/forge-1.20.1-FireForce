//package net.mintteacup.fireforcemod.entity.custom;
//
//import it.unimi.dsi.fastutil.objects.ObjectArrayList;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.FlyingMob;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.PathfinderMob;
//import net.minecraft.world.entity.ai.Brain;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.control.FlyingMoveControl;
//import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
//import net.minecraft.world.entity.ai.navigation.PathNavigation;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.mintteacup.fireforcemod.entity.ai.behaviour.MoveToFlyingTarget;
//import net.tslat.smartbrainlib.api.SmartBrainOwner;
//import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
//import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
//import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomFlyingTarget;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
//import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
//import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
//import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
//import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
//import software.bernie.geckolib.animatable.GeoEntity;
//import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
//import software.bernie.geckolib.core.animation.AnimatableManager;
//import software.bernie.geckolib.util.GeckoLibUtil;
//import java.util.List;
//
//public class AdollaBugEntity extends PathfinderMob implements GeoEntity, SmartBrainOwner<AdollaBugEntity> {
//
//    private final AnimatableInstanceCache cache;
//
//    public AdollaBugEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
//        super(entityType, level);
//        this.cache = GeckoLibUtil.createInstanceCache(this);
//        this.moveControl = new FlyingMoveControl(this, 20, true);
//        this.navigation = new FlyingPathNavigation(this, level);
//    }
//
//    public static AttributeSupplier.Builder createAttributes() {
//        return PathfinderMob.createMobAttributes()
//                .add(Attributes.MAX_HEALTH, 10.0)
//                .add(Attributes.FLYING_SPEED, 0.6000000238418579)
//                .add(Attributes.MOVEMENT_SPEED, 0.6000000238418579)
//                .add(Attributes.ATTACK_DAMAGE, 2.0)
//                .add(Attributes.FOLLOW_RANGE, 16.0);
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        // Animation controllers will go here later
//    }
//
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return cache;
//    }
//
//    @Override
//    protected Brain.Provider<?> brainProvider() {
//        return new SmartBrainProvider<>(this);
//    }
//
//    @Override
//    protected void customServerAiStep() {
//        tickBrain(this);
//        System.out.println(this + " brain ticking");
//    }
//
//    @Override
//    protected PathNavigation createNavigation(Level level) {
//        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
//        navigation.setCanFloat(true);
//        return navigation;
//    }
//
//    @Override
//    public List<ExtendedSensor<AdollaBugEntity>> getSensors() {
//        return ObjectArrayList.of(
//                new NearbyLivingEntitySensor<>(),
//                new HurtBySensor<>()
//        );
//    }
//
//    @Override
//    public BrainActivityGroup<AdollaBugEntity> getCoreTasks() {
//        return BrainActivityGroup.coreTasks(
//                new LookAtTarget<>(),
//                new MoveToWalkTarget<>()
//        );
//    }
//
//    @Override
//    public BrainActivityGroup<AdollaBugEntity> getIdleTasks() {
//        return BrainActivityGroup.idleTasks(
//                new FirstApplicableBehaviour<AdollaBugEntity>(
//                        new TargetOrRetaliate<>(),
//                        new SetPlayerLookTarget<>(),
//                        new SetRandomLookTarget<>()
//                ),
//                new SetRandomFlyingTarget<>().setRadius(10, 10).speedModifier(0.6f)
//        );
//    }
//
//    @Override
//    public BrainActivityGroup<AdollaBugEntity> getFightTasks() {
//        return BrainActivityGroup.fightTasks(
//                new InvalidateAttackTarget<>(),
//                new AnimatableMeleeAttack<>(0)
//        );
//    }
//
//    @Override
//    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
//        // No fall damage for flying mobs
//    }
//
//    @Override
//    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
//        return false;
//    }
//}
