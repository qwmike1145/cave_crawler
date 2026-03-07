package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CaveCrawlerFleeGoal extends Goal {
    private final CaveCrawlerEntity caveCrawler;
    private final double fleeDistance;
    private final int fleeSeconds;
    private int fleeTicks;

    public CaveCrawlerFleeGoal(final CaveCrawlerEntity caveCrawler, double fleeDistance, int fleeSeconds) {
        this.caveCrawler = caveCrawler;
        this.fleeDistance = fleeDistance;
        this.fleeSeconds = fleeSeconds;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Flee when recently hurt and a target exists
        return caveCrawler.getLastHurtByMobTimestamp() > caveCrawler.tickCount - 40
                && caveCrawler.getLastHurtByMob() != null
                && !caveCrawler.isFleeing;
    }

    @Override
    public boolean canContinueToUse() {
        return caveCrawler.isFleeing && fleeTicks > 0;
    }

    @Override
    public void start() {
        caveCrawler.isFleeing = true;
        caveCrawler.getEntityData().set(CaveCrawlerEntity.FLEEING_ACCESSOR, true);
        caveCrawler.setTarget(null);
        fleeTicks = Utils.secondsToTicks(fleeSeconds);
        caveCrawler.playFleeSound();
    }

    @Override
    public void tick() {
        --fleeTicks;

        LivingEntity threat = caveCrawler.getLastHurtByMob();

        if (threat != null) {
            // Run away from the threat
            Vec3 awayDir = caveCrawler.position().subtract(threat.position()).normalize();
            Vec3 fleeTarget = caveCrawler.position().add(awayDir.scale(fleeDistance));

            caveCrawler.getNavigation().moveTo(fleeTarget.x, fleeTarget.y, fleeTarget.z, 1.2);
        }
    }

    @Override
    public void stop() {
        caveCrawler.isFleeing = false;
        caveCrawler.getEntityData().set(CaveCrawlerEntity.FLEEING_ACCESSOR, false);
        fleeTicks = 0;
    }
}
