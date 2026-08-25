package com.dev;

public enum Urgency {
    ASAP(1, "asap"),
    HIGH(2, "high"),
    MEDIUM(3, "medium"),
    LOW(4, "low");

    private final String name;
    private final int level;

    Urgency(int level, String name) {
        this.name = name;
        this.level = level;
    }

    public boolean isMoreImportantThan(Urgency other) {
        return this.level < other.level;
    }
};
