package com.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import com.scheduler.domain.Schedule;
import com.scheduler.domain.Team;
import com.scheduler.domain.Week;

public class Scheduler 
{
    public static void rivalWeekSetter(Schedule schedule) {
        Week week1 = new Week(1);

        List<Team> handled = new ArrayList<>();

        for (Team team : schedule.getTeams()) {
            Team rival = team.getRival();

            if (handled.contains(team) || handled.contains(rival)) {
                continue;
            }

            week1.addGame(Pair.of(rival, team));
            rival.addAwayGame();
            team.addHomeGame();
            rival.addTeamPlayed(team);
            team.addTeamPlayed(rival);
            rival.removeRemainingOpponent(team);
            team.removeRemainingOpponent(rival);

            handled.add(team);
            handled.add(rival);
        }

        schedule.addWeek(week1);
    }

    public static void roundRobinSchedule(Schedule schedule) {
        List<Team> teams = schedule.getTeams();
        List<Team> circle = new ArrayList<>();
        List<Team> awayRivals = new ArrayList<>();

        for (Team t : teams) {
            if (t.getAwayGames() == 1) {
                awayRivals.add(t);
            }
        }

        Team[] list1 = new Team[6];
        Team[] list2 = new Team[6];

        for (int i = 0; i < awayRivals.size(); i++) {
            list1[i] = awayRivals.get(i);
            list2[i] = list1[i].getRival();
        }

        Collections.reverse(Arrays.asList(list2));
        circle.addAll(awayRivals);
        circle.addAll(Arrays.asList(list2));

        List<Team> initialCircle = new ArrayList<>(circle);
        int week = 2;

        do {
            Team tail = circle.remove(circle.size() - 1);
            circle.add(1, tail);

            List<Pair<Team, Team>> games = new ArrayList<>();
            Week weekObject = new Week(week);
            for (int i = 0; i < 6; i++) {
                Team home = circle.get(i);
                Team away = circle.get(12 - 1 - i);
                // games.add(Pair.of(away, home));
                weekObject.addGame(Pair.of(away, home));
                away.addAwayGame();
                home.addHomeGame();
                away.addTeamPlayed(home);
                home.addTeamPlayed(away);
            }

            boolean hasRival = games.stream().anyMatch(p -> p.getLeft().getRival() == p.getRight());

            if (!hasRival && week <= 11) {
                schedule.addWeek(weekObject);
                week++;
            }

        } while (!circle.equals(initialCircle) && week <= 11);
    }

    public static void remainderMatchups(Schedule schedule) {
        List<Integer> repeatMatchups = new ArrayList<>();

        while (repeatMatchups.size() < 3) {
            Random random = new Random();
            int num = random.nextInt(10) + 1;
            if (!repeatMatchups.contains(num)) {
                repeatMatchups.add(num);
            }
        }

        int count = 1;

        for (Integer matchup : repeatMatchups) {
            Week week = new Week(schedule.getWeek(matchup));
            week.setWeekNumber(11 + count);
            schedule.addWeek(week);
            count++;
        }

        // System.out.println(repeatMatchups);
    }

    public static void balanceHomeAway(Schedule schedule) {
        boolean changed = true;

        while (changed) {
            changed = false;

            for (Team team : schedule.getTeams()) {
                int diff = team.getHomeGames() - team.getAwayGames();

                if (Math.abs(diff) <= 1) continue;

                for (Week week : schedule.getWeeks()) {
                    if (week.getWeekNumber() == 1) continue;

                    Pair<Team, Team> game = week.findGameWith(team);
                    if (game == null) continue;

                    Team away = game.getLeft();
                    Team home = game.getRight();

                    if (home == team && diff > 1) {
                        // Check if away team can absorb the flip
                        if (away.getAwayGames() - away.getHomeGames() >= 1) {
                            // Flip team to away
                            week.replaceGame(Pair.of(team, away));
                            team.decrementHomeGame();
                            team.addAwayGame();
                            away.decrementAwayGame();
                            away.addHomeGame();
                            changed = true;
                            break;
                        }
                    } else if (away == team && diff < -1) {
                        if (home.getHomeGames() - home.getAwayGames() >= 1) {
                            // Flip team to home
                            week.replaceGame(Pair.of(home, team));
                            team.decrementAwayGame();
                            team.addHomeGame();
                            home.decrementHomeGame();
                            home.addAwayGame();
                            changed = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public static void printSchedule(Schedule schedule) {
        for (Team team : schedule.getTeams()) {
            System.out.println(team.getName());
            System.out.println("Home Games: " + team.getHomeGames());
            System.out.println("Away Games: " + team.getAwayGames());
            System.out.println("Total Games: " + (team.getHomeGames() + team.getAwayGames()));
            // System.out.println("Distinct Teams: " + team.getTeamsPlayed().size());
        }
    }
}
