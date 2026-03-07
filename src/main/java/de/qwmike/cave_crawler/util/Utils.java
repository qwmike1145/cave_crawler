package de.qwmike.cave_crawler.util;

import de.qwmike.cave_crawler.config.ServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.Path;

import java.util.Optional;

public class Utils {

    public static int secondsToTicks(int seconds) {
        return seconds * 20;
    }

    public static boolean isValidTarget(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!entity.isAlive()) {
            return false;
        }
        if (entity instanceof Player player) {
            if (player.isSpectator() || player.isCreative()) {
                return false;
            }
            if (!ServerConfig.TARGET_INVISIBLE.get() && player.isInvisible()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOnSurface(final Player player) {
        int surfaceY = player.level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, player.blockPosition().getX(), player.blockPosition().getZ());
        return player.blockPosition().getY() >= surfaceY - 1;
    }

    /**
     * Returns "" for default textures, or "_updated" if the updated texture config is enabled.
     * Since ClientConfig is unused in this port, always returns "".
     */
    public static String getTextureAppend() {
        return "";
    }

    public static <T extends Mob> Optional<T> trySpawnMob(
            final ServerPlayer victim,
            final EntityType<T> entityType,
            final MobSpawnType spawnType,
            final ServerLevel level,
            final BlockPos center,
            int attempts,
            int xzOffset,
            int yOffset,
            final SpawnUtil.Strategy strategy
    ) {
        Optional<T> result = SpawnUtil.trySpawnMob(entityType, spawnType, level, center, attempts, xzOffset, yOffset, strategy);

        if (result.isPresent() && ServerConfig.CHECK_PATH_TO_SPAWN.get()) {
            T mob = result.get();
            PathNavigation nav = mob.getNavigation();
            Path path = nav.createPath(victim, 0);

            if (path == null || !path.canReach()) {
                mob.discard();
                return Optional.empty();
            }
        }

        return result;
    }
}
