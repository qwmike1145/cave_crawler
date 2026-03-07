package de.qwmike.cave_crawler.entities.goals;

public enum Roll {
    WANDER(0),
    APPROACH(1),
    RETREAT(2),
    STROLL(3);

    private final int value;

    Roll(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Roll fromValue(int value) {
        for (Roll roll : values()) {
            if (roll.value == value) {
                return roll;
            }
        }
        return WANDER;
    }
}
