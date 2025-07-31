package com.scheduler.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Schedule {
    private List<Week> weeks;
    private List<Team> teams;
    private int numberOfWeeks;
    
    public Schedule(int numberOfWeeks, List<Team> teams) {
        this.weeks = new ArrayList<>();
        this.teams = teams;
        this.numberOfWeeks = numberOfWeeks;
    }

    public void addWeek(Week week) {
        this.weeks.add(week);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void printSchedule() {
        for (Week week : weeks) {
            week.printWeek();
        }
    }

    public Week getWeek(int index) {
        return weeks.get(index - 1);
    }

    public void moreThanTwice() {
        // Check if a team plays another team > 2 times
        Map<Pair<Team, Team>, Integer> matchupCount = new HashMap<>();
        int count = 0;

        for (Week week : weeks) {
            for (Pair<Team, Team> matchup : week.getGames()) {
                Team team1 = matchup.getLeft();
                Team team2 = matchup.getRight();

                // Normalize the key so (A, B) and (B, A) are treated the same
                Pair<Team, Team> pair = team1.getName().compareTo(team2.getName()) < 0
                    ? new ImmutablePair<>(team1, team2)
                    : new ImmutablePair<>(team2, team1);

                matchupCount.put(pair, matchupCount.getOrDefault(pair, 0) + 1);
            }
        }

        for (Map.Entry<Pair<Team, Team>, Integer> entry : matchupCount.entrySet()) {
            if (entry.getValue() > 1) {
                Pair<Team, Team> pair = entry.getKey();
                System.out.println(pair.getLeft().getName() + " and " + pair.getRight().getName()
                    + " played each other " + entry.getValue() + " times.");
                count++;
            }
        }

        if (count > 0) {
            System.out.println("ERRORS: " + count);
        }
        else {
            System.out.println("Duplicate Matchup Alert");
        }
    }

    public boolean isRival(Team a, Team b) {
        return a.getRival() == b && b.getRival() == a;
    }
}
