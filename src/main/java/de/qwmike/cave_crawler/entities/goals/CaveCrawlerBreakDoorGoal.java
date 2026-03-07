package de.qwmike.cave_crawler.entities.goals;

import de.qwmike.cave_crawler.config.ServerConfig;
import de.qwmike.cave_crawler.entities.CaveCrawlerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;

import java.util.function.Predicate;

public class CaveCrawlerBreakDoorGoal extends BreakDoorGoal {
    public CaveCrawlerBreakDoorGoal(final CaveCrawlerEntity caveDweller, final Predicate<Difficulty> validDifficulty) {
        super(caveDweller, ServerConfig.BREAK_DOOR_TIME.get() * 20, validDifficulty);
    }
}
