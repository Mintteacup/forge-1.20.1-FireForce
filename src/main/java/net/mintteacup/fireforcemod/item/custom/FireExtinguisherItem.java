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

    public FireExtinguisherItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand); // Starts the "holding" state

        return InteractionResultHolder.consume(itemstack); // Changed to CONSUME to prevent swinging
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLiving, ItemStack pStack, int pRemainingUseDuration) {

        if (pLiving instanceof Player pPlayer) {

            // Server-side fire extinguishing logic
            if (!pLevel.isClientSide && pRemainingUseDuration % 5 == 0) {
                extinguishFires(pLevel, pPlayer);
            }

            // Client-side particle effects
            if (pLevel.isClientSide && pRemainingUseDuration % 2 == 0) {
                spawnExtinguisherParticles(pLevel, pPlayer);
            }
        }
    }

    private void extinguishFires(Level pLevel, Player pPlayer) {

        Vec3 lookVec = pPlayer.getLookAngle();
        double maxDistance = 5.0;

        // Loop from player position to max distance
        for (double distance = 1; distance <= maxDistance; distance += 0.5) {
            Vec3 checkPos = pPlayer.getEyePosition().add(lookVec.scale(distance));
            BlockPos blockPos = BlockPos.containing(checkPos);
            BlockState state = pLevel.getBlockState(blockPos);

            // Stop if hitting a solid block
            if (state.isSolidRender(pLevel, blockPos)) {
                break; // Exit the loop early
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
                    .subtract(0, 0.2, 0) // Adjust height slightly
                    .add(lookVec.scale(0.5)); // Start slightly in front of player

            // Create a cone-shaped spray of particles
            for (int i = 0; i < 15; i++) {
                double distance = 0.5 + pLevel.random.nextDouble() * 4.5;
                double spread = distance * 0.3; // Wider spread at greater distances

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
                        lookVec.x * 0.05 + (pLevel.random.nextDouble() - 0.5) * 0.02,
                        lookVec.y * 0.05 + (pLevel.random.nextDouble() - 0.5) * 0.02,
                        lookVec.z * 0.05 + (pLevel.random.nextDouble() - 0.5) * 0.02
                );

                // Occasionally add white smoke particles
//                if (i % 3 == 0) {
//                    clientLevel.addParticle(
//                            ParticleTypes.LARGE_SMOKE,
//                            spawnPos.x, spawnPos.y, spawnPos.z,
//                            lookVec.x * 0.03 + (pLevel.random.nextDouble() - 0.5) * 0.01,
//                            lookVec.y * 0.03 + (pLevel.random.nextDouble() - 0.5) * 0.01,
//                            lookVec.z * 0.03 + (pLevel.random.nextDouble() - 0.5) * 0.01
//                    );
//                }
            }
        }

        }


//    @Override
//    public void onUseTick(Level pLevel, LivingEntity pLiving, ItemStack pStack, int pRemainingUseDuration) {
//        if (!pLevel.isClientSide() && pLiving instanceof Player pPlayer) {
//            if (pRemainingUseDuration % 5 == 0) { // Throttle to 4x/sec
//                Vec3 lookVec = pPlayer.getLookAngle();
//                double maxDistance = 5.0;
//                //double radius = 1.5; // Spray width at max distance
//
//                // Loop from player position to max distance
//                for (double distance = 1; distance <= maxDistance; distance += 0.5) {
//                    Vec3 checkPos = pPlayer.getEyePosition().add(lookVec.scale(distance));
//                    BlockPos blockPos = BlockPos.containing(checkPos);
//                    BlockState state = pLevel.getBlockState(blockPos);
//
//                    // Stop if hitting a solid block
//                    if (state.isSolidRender(pLevel, blockPos)) {
//                        break; // Exit the loop early
//                    }
//
//                    if (state.is(Blocks.FIRE)) {
//                        pLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
//                        pLevel.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH,
//                                SoundSource.BLOCKS, 0.5F, 1.0F);
//
//                    }
//                }
//            }
//        }
//    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // Plays the bow-pulling animation while holding
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Arbitrarily large duration (holds indefinitely)
    }

}