package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.config.ServerConfig;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CaveCrawlerChaseGoal extends MeleeAttackGoal {
    private final CaveCrawlerEntity caveCrawler;
    private final boolean canTeleport;
    private int teleportCooldown;

    public CaveCrawlerChaseGoal(final CaveCrawlerEntity caveCrawler, boolean canTeleport) {
        super(caveCrawler, 1.0, true);
        this.caveCrawler = caveCrawler;
        this.canTeleport = canTeleport;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = caveCrawler.getTarget();
        return target != null && Utils.isValidTarget(target) && !caveCrawler.isFleeing;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = caveCrawler.getTarget();
        return target != null && Utils.isValidTarget(target) && !caveCrawler.isFleeing;
    }

    @Override
    public void start() {
        // Reset leave timer to chase-specific duration
        caveCrawler.ticksTillRemove = Utils.secondsToTicks(ServerConfig.TIME_UNTIL_LEAVE_CHASE.get());
        super.start();
    }

    @Override
    public void tick() {
        LivingEntity target = caveCrawler.getTarget();

        if (target == null) {
            return;
        }

        caveCrawler.playChaseSound();

        // Teleport if path is stuck
        if (canTeleport && teleportCooldown <= 0) {
            if (caveCrawler.getNavigation().isStuck()) {
                boolean teleported = caveCrawler.teleportToTarget();
                if (teleported) {
                    teleportCooldown = Utils.secondsToTicks(5);
                }
            }
        }

        if (teleportCooldown > 0) {
            --teleportCooldown;
        }

        super.tick();
    }

    @Override
    public void stop() {
        teleportCooldown = 0;
        super.stop();
    }
}
