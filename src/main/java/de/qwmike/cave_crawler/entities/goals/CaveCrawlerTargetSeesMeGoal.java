package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;

public class CaveCrawlerTargetSeesMeGoal extends TargetGoal {
    private final CaveCrawlerEntity caveCrawler;
    private Player targetPlayer;

    public CaveCrawlerTargetSeesMeGoal(final CaveCrawlerEntity caveCrawler) {
        super(caveCrawler, false);
        this.caveCrawler = caveCrawler;
    }

    @Override
    public boolean canUse() {
        // Don't override an existing target
        if (caveCrawler.getTarget() != null) {
            return false;
        }

        // Scan for a player who is directly looking at the Cave Dweller
        for (Player player : caveCrawler.level.players()) {
            if (!Utils.isValidTarget(player)) {
                continue;
            }
            if (caveCrawler.isLookingAtMe(player, true)) {
                targetPlayer = player;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = caveCrawler.getTarget();
        return target != null && Utils.isValidTarget(target);
    }

    @Override
    public void start() {
        caveCrawler.setTarget(targetPlayer);
        super.start();
    }

    @Override
    public void stop() {
        targetPlayer = null;
        super.stop();
    }
}
