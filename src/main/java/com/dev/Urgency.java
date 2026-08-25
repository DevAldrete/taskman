package com.dev;

import java.util.NoSuchElementException;
import java.util.Objects;

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

    public static Urgency fromStr(String str) {
        if (Objects.equals(str, "asap")) {
            return Urgency.ASAP;
        } else if (Objects.equals(str, "high")) {
            return Urgency.HIGH;
        } else if (Objects.equals(str, "medium")) {
            return Urgency.MEDIUM;
        } else if (Objects.equals(str, "low")) {
            return Urgency.LOW;
        } else {
            throw new NoSuchElementException("unknown element with that urgency level");
        }
    }
};
