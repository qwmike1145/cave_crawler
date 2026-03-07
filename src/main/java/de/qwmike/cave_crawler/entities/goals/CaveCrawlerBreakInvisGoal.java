package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.config.ServerConfig;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class CaveCrawlerBreakInvisGoal extends Goal {
    private final CaveCrawlerEntity caveCrawler;
    private Player targetPlayer;

    public CaveCrawlerBreakInvisGoal(final CaveCrawlerEntity caveCrawler) {
        this.caveCrawler = caveCrawler;
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        if (!ServerConfig.TARGET_INVISIBLE.get()) {
            return false;
        }

        // Only attempt when we have a target who is invisible
        if (caveCrawler.getTarget() instanceof Player player) {
            if (player.isInvisible() && player.hasEffect(MobEffects.INVISIBILITY)) {
                targetPlayer = player;
                return caveCrawler.distanceTo(player) <= 4.0;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false; // One-shot goal
    }

    @Override
    public void start() {
        if (targetPlayer != null) {
            // Remove the invisibility effect when the cave crawler gets close enough
            targetPlayer.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    @Override
    public void stop() {
        targetPlayer = null;
    }
}
