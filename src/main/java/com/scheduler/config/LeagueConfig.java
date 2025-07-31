package com.scheduler.config;

public class LeagueConfig {
    final int numberOfWeeks = 14;
    boolean rivalPriority = true;

    private LeagueConfig() {
        // Private constructor to prevent instantiation
    }

    private static LeagueConfig instance = null;

    public static LeagueConfig getInstance() {
        if (instance == null) {
            instance = new LeagueConfig();
        }
        return instance;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public boolean getRivalPriority() {
        return rivalPriority;
    }
}
