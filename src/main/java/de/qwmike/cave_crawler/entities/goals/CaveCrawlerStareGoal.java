package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.config.ServerConfig;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class CaveCrawlerStareGoal extends Goal {
    private final CaveCrawlerEntity caveCrawler;
    private Player staringPlayer;
    private int stareEndTicks;

    public CaveCrawlerStareGoal(final CaveCrawlerEntity caveCrawler) {
        this.caveCrawler = caveCrawler;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Don't stare while actively chasing or fleeing
        if (caveCrawler.isAggressive() || caveCrawler.isFleeing) {
            return false;
        }

        // Look for any player staring directly at us
        for (Player player : caveCrawler.level.players()) {
            if (!Utils.isValidTarget(player)) {
                continue;
            }
            if (caveCrawler.isLookingAtMe(player, true)
                    && caveCrawler.distanceTo(player) <= ServerConfig.SPOTTING_RANGE.get()) {
                staringPlayer = player;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (caveCrawler.isAggressive() || caveCrawler.isFleeing) {
            return false;
        }
        if (stareEndTicks <= 0) {
            return false;
        }
        if (staringPlayer == null || !Utils.isValidTarget(staringPlayer)) {
            return false;
        }
        return caveCrawler.isLookingAtMe(staringPlayer, false);
    }

    @Override
    public void start() {
        stareEndTicks = Utils.secondsToTicks(3);
        caveCrawler.setSpotted(true);
        caveCrawler.pleaseStopMoving = true;
        caveCrawler.getNavigation().stop();
    }

    @Override
    public void tick() {
        --stareEndTicks;

        if (staringPlayer != null) {
            caveCrawler.getLookControl().setLookAt(staringPlayer, 30.0F, 30.0F);
        }
    }

    @Override
    public void stop() {
        caveCrawler.setSpotted(false);
        caveCrawler.pleaseStopMoving = false;
        staringPlayer = null;
        stareEndTicks = 0;
    }
}
