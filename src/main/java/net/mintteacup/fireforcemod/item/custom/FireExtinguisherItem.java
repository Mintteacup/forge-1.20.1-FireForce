package net.mintteacup.fireforcemod.item.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FireExtinguisherItem extends Item {

    // ========== CONFIGURATION CONSTANTS ==========
    private static final int PARTICLES_PER_TICK = 15;
    private static final double SPREAD_FACTOR = 0.3;
    private static final double MAX_EXTINGUISH_DISTANCE = 5.0;
    private static final double PARTICLE_SPEED = 0.05;
    private static final double START_OFFSET_Y = -0.2;
    private static final double START_OFFSET_FORWARD = 0.5;
    // =============================================

    public FireExtinguisherItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLiving, ItemStack pStack, int pRemainingUseDuration) {
        if (pLiving instanceof Player pPlayer) {
            if (!pLevel.isClientSide && pRemainingUseDuration % 5 == 0) {
                extinguishFires(pLevel, pPlayer);
            }
            if (pLevel.isClientSide && pRemainingUseDuration % 2 == 0) {
                spawnExtinguisherParticles(pLevel, pPlayer);
            }
        }
    }

    private void extinguishFires(Level pLevel, Player pPlayer) {
        Vec3 lookVec = pPlayer.getLookAngle();

        for (double distance = 1; distance <= MAX_EXTINGUISH_DISTANCE; distance += 0.5) {
            Vec3 checkPos = pPlayer.getEyePosition().add(lookVec.scale(distance));
            BlockPos blockPos = BlockPos.containing(checkPos);
            BlockState state = pLevel.getBlockState(blockPos);

            if (state.isSolidRender(pLevel, blockPos)) {
                break;
            }

            if (state.is(Blocks.FIRE)) {
                pLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                pLevel.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.BLOCKS, 0.5F, 1.0F);
            }
        }
    }

    private void spawnExtinguisherParticles(Level pLevel, Player pPlayer) {
        if (pLevel instanceof ClientLevel clientLevel) {
            Vec3 lookVec = pPlayer.getLookAngle();
            Vec3 startPos = pPlayer.getEyePosition()
                    .subtract(0, START_OFFSET_Y, 0)
                    .add(lookVec.scale(START_OFFSET_FORWARD));

            for (int i = 0; i < PARTICLES_PER_TICK; i++) {
                double distance = 0.5 + pLevel.random.nextDouble() * MAX_EXTINGUISH_DISTANCE;
                double spread = distance * SPREAD_FACTOR;

                Vec3 spawnPos = startPos.add(
                        lookVec.x * distance,
                        lookVec.y * distance,
                        lookVec.z * distance
                ).add(
                        (pLevel.random.nextDouble() - 0.5) * spread,
                        (pLevel.random.nextDouble() - 0.5) * spread,
                        (pLevel.random.nextDouble() - 0.5) * spread);

                clientLevel.addParticle(
                        ParticleTypes.CLOUD,
                        spawnPos.x, spawnPos.y, spawnPos.z,
                        lookVec.x * PARTICLE_SPEED + (pLevel.random.nextDouble() - 0.5) * 0.02,
                        lookVec.y * PARTICLE_SPEED + (pLevel.random.nextDouble() - 0.5) * 0.02,
                        lookVec.z * PARTICLE_SPEED + (pLevel.random.nextDouble() - 0.5) * 0.02
                );
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
}