package com.dev;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Nivel de urgencia de una tarea. Cada valor tiene un nivel numerico
 * (menor = mas urgente) y un nombre textual usado como entrada del usuario.
 */
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

    /** Indica si esta urgencia es mas importante que 'other' (nivel menor). */
    public boolean isMoreImportantThan(Urgency other) {
        return this.level < other.level;
    }

    /** Convierte una cadena de texto a Urgency. Lanza NoSuchElementException si no coincide. */
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
