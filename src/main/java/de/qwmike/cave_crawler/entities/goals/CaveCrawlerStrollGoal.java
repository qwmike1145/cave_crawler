package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class CaveCrawlerStrollGoal extends Goal {
    private static final Random RANDOM = new Random();

    private final CaveCrawlerEntity caveCrawler;
    private final double speedModifier;
    private int rollChangeCooldown;

    public CaveCrawlerStrollGoal(final CaveCrawlerEntity caveCrawler, double speedModifier) {
        this.caveCrawler = caveCrawler;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !caveCrawler.isAggressive() && !caveCrawler.isFleeing && !caveCrawler.pleaseStopMoving;
    }

    @Override
    public boolean canContinueToUse() {
        return !caveCrawler.isAggressive() && !caveCrawler.isFleeing && !caveCrawler.pleaseStopMoving;
    }

    @Override
    public void start() {
        rollChangeCooldown = Utils.secondsToTicks(8 + RANDOM.nextInt(12));
        caveCrawler.reRoll();
        pickNewDestination();
    }

    @Override
    public void tick() {
        if (--rollChangeCooldown <= 0) {
            rollChangeCooldown = Utils.secondsToTicks(8 + RANDOM.nextInt(12));
            caveCrawler.reRoll();
            pickNewDestination();
        }

        // If stuck, pick a new destination
        if (caveCrawler.getNavigation().isDone()) {
            pickNewDestination();
        }
    }

    @Override
    public void stop() {
        caveCrawler.getNavigation().stop();
    }

    private void pickNewDestination() {
        Roll roll = caveCrawler.currentRoll;

        switch (roll) {
            case STROLL -> {
                // Stand still - stare animation handled by entity
                caveCrawler.getNavigation().stop();
            }
            case APPROACH -> {
                // Slowly move toward nearest player
                Player nearestPlayer = getNearestPlayer();
                if (nearestPlayer != null) {
                    caveCrawler.getNavigation().moveTo(nearestPlayer, speedModifier);
                } else {
                    wander();
                }
            }
            case RETREAT -> {
                // Move away from nearest player
                Player nearestPlayer = getNearestPlayer();
                if (nearestPlayer != null) {
                    Vec3 awayDir = caveCrawler.position().subtract(nearestPlayer.position()).normalize();
                    Vec3 retreatPos = caveCrawler.position().add(awayDir.scale(16));
                    caveCrawler.getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, speedModifier);
                } else {
                    wander();
                }
            }
            default -> wander(); // WANDER
        }
    }

    private void wander() {
        BlockPos pos = caveCrawler.blockPosition();
        int range = 12;
        BlockPos target = new BlockPos(
                pos.getX() + RANDOM.nextInt(range * 2) - range,
                pos.getY() + RANDOM.nextInt(4) - 2,
                pos.getZ() + RANDOM.nextInt(range * 2) - range
        );
        caveCrawler.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), speedModifier);
    }

    private Player getNearestPlayer() {
        List<? extends Player> players = caveCrawler.level.players();
        Player nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for (Player player : players) {
            if (!Utils.isValidTarget(player)) continue;
            double dist = caveCrawler.distanceToSqr(player);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearest = player;
            }
        }

        return nearest;
    }
}
