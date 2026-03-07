package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class CaveCrawlerTargetTooCloseGoal extends NearestAttackableTargetGoal<Player> {
    private final CaveCrawlerEntity caveCrawler;
    private final double triggerRange;

    public CaveCrawlerTargetTooCloseGoal(final CaveCrawlerEntity caveCrawler, double triggerRange) {
        super(caveCrawler, Player.class, true);
        this.caveCrawler = caveCrawler;
        this.triggerRange = triggerRange;
    }

    @Override
    public boolean canUse() {
        // Only activate when there is no current target
        if (caveCrawler.getTarget() != null) {
            return false;
        }

        for (Player player : caveCrawler.level.players()) {
            if (!Utils.isValidTarget(player)) {
                continue;
            }
            if (caveCrawler.distanceTo(player) <= triggerRange) {
                caveCrawler.setTarget(player);
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
}
