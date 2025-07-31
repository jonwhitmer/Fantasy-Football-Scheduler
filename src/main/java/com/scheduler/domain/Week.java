package com.scheduler.domain;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Week {
    private int weekNumber;
    private String weekName;
    private List<Pair<Team, Team>> games;

    public Week(int weekNumber) {
        this.weekNumber = weekNumber;
        this.weekName = "Week " + weekNumber;
        this.games = new ArrayList<>();
    }

    public Week(Week week) {
        this.weekNumber = week.getWeekNumber();
        this.weekName = "Week " + week.getWeekNumber();
        this.games = new ArrayList<>(week.getGames());
    }

    public void addGame(Pair<Team, Team> game) {
        games.add(game);
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
        this.weekName = "Week " + weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public List<Pair<Team, Team>> getGames() {
        return games;
    }

    public Pair<Team, Team> findGameWith(Team t) {
        for (Pair<Team, Team> g : games) {
            if (g.getLeft().equals(t) || g.getRight().equals(t)) {
                return g;
            }
        }

        return null;
    }

    public void replaceGame(Pair<Team,Team> newGame) {
        for (int i = 0; i < games.size(); i++) {
            Pair<Team,Team> g = games.get(i);
            if ((g.getLeft().equals(newGame.getLeft()) && g.getRight().equals(newGame.getRight())) ||
                (g.getLeft().equals(newGame.getRight()) && g.getRight().equals(newGame.getLeft()))) {
            games.set(i, newGame);
            return;
            }
        }
    }

    public void printWeek() {
        System.out.println();
        System.out.println(this.weekName);
        for (Pair<Team, Team> game : games) {
            System.out.println(game.getLeft().getName() + " vs " + game.getRight().getName());
        }
    }


}
