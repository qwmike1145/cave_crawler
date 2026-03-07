package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import de.qwmike.cave_crawler.util.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class CustomHurtByTargetGoal extends HurtByTargetGoal {
    private final CaveCrawlerEntity caveCrawler;

    public CustomHurtByTargetGoal(final CaveCrawlerEntity caveCrawler) {
        super(caveCrawler);
        this.caveCrawler = caveCrawler;
    }

    @Override
    public boolean canUse() {
        LivingEntity attacker = caveCrawler.getLastHurtByMob();

        if (attacker == null || !Utils.isValidTarget(attacker)) {
            return false;
        }

        return super.canUse();
    }
}
