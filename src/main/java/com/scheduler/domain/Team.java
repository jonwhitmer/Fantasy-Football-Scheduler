package com.scheduler.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team {
    private String name;
    private String owner;
    private String ownerFirstName;
    private String ownerLastName;
    private Team rival;
    private int homeGames;
    private int awayGames;
    private List<Team> teamsPlayed; // RR
    private Set<Team> remainingOpponents = new HashSet<>(); // RR

    public Team(String name, String owner) {
        this.name = name;
        this.ownerFirstName = owner.split(" ")[0];
        this.ownerLastName = owner.split(" ")[1];
        this.owner = owner;
        this.homeGames = 0;
        this.awayGames = 0;
        this.rival = null;
        this.teamsPlayed = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public int getHomeGames() {
        return homeGames;
    }

    public int getAwayGames() {
        return awayGames;
    }

    public void addHomeGame() {
        this.homeGames++;
    }

    public void addAwayGame() {
        this.awayGames++;
    }

    public void decrementHomeGame() {
        this.homeGames--;
    }

    public void decrementAwayGame() {
        this.awayGames--;
    }

    public void setRival(Team rival) {
        this.rival = rival;
    }

    public Team getRival() {
        return rival;
    }

    public void addTeamPlayed(Team team) {
        this.teamsPlayed.add(team);
    }

    public List<Team> getTeamsPlayed() {
        return teamsPlayed;
    }

    public void addRemainingOpponent(Team team) {
        this.remainingOpponents.add(team);
    }

    public Set<Team> getRemainingOpponents() {
        return remainingOpponents;
    }

    public void removeRemainingOpponent(Team team) {
        this.remainingOpponents.remove(team);
    }
}
