package de.qwmike.cave_crawler.util;

import de.qwmike.cave_crawler.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;

import java.util.Random;

public class Timer {
    private static final Random RANDOM = new Random();

    public int currentSpawn;
    public int targetSpawn;
    public int currentNoise;
    public int targetNoise;
    public ServerPlayer currentVictim;

    public Timer() {
        resetSpawnTimer();
        resetNoiseTimer();
    }

    public boolean isSpawnTimerReached() {
        return currentSpawn >= targetSpawn;
    }

    public boolean isNoiseTimerReached() {
        return currentNoise >= targetNoise;
    }

    public void resetSpawnTimer() {
        currentSpawn = 0;
        int minTicks = Utils.secondsToTicks(ServerConfig.CAN_SPAWN_MIN.get());
        int maxTicks = Utils.secondsToTicks(ServerConfig.CAN_SPAWN_MAX.get());
        int range = maxTicks - minTicks;

        targetSpawn = minTicks + (range > 0 ? RANDOM.nextInt(range) : 0);

        // Optional cooldown
        if (RANDOM.nextDouble() < ServerConfig.CAN_SPAWN_COOLDOWN_CHANCE.get()) {
            targetSpawn += Utils.secondsToTicks(ServerConfig.CAN_SPAWN_COOLDOWN.get());
        }
    }

    public void resetNoiseTimer() {
        currentNoise = 0;
        int minTicks = Utils.secondsToTicks(ServerConfig.RESET_NOISE_MIN.get());
        int maxTicks = Utils.secondsToTicks(ServerConfig.RESET_NOISE_MAX.get());
        int range = maxTicks - minTicks;

        targetNoise = minTicks + (range > 0 ? RANDOM.nextInt(range) : 0);
    }

    @Override
    public String toString() {
        return "Timer{spawn=" + currentSpawn + "/" + targetSpawn
                + ", noise=" + currentNoise + "/" + targetNoise
                + ", victim=" + (currentVictim != null ? currentVictim.getName().getString() : "none") + "}";
    }
}
