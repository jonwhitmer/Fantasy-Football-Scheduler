package com.scheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import com.opencsv.CSVReader;
import com.scheduler.domain.Team;

public class TeamAssembler {
    // Read the CSV File found in the project root (Teams.csv)
    public static List<Team> parseTeams(String filePath) throws Exception {
        List<Team> teams = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // skip the header line
            while ((line = reader.readNext()) != null) {
                if (line.length >= 2) {
                    String teamName = line[0].trim();
                    String managerName = line[1].trim();
                    teams.add(new Team(teamName, managerName));
                }
            }
        }

        if (teams.size() > 12) {
            throw new IllegalArgumentException("ERROR - TOO MANY TEAMS");
        }

        if (teams.size() < 12) {
            throw new IllegalArgumentException("ERROR - NOT ENOUGH TEAMS");
        }
        
        return teams;
    }

    public static List<Team> rivalSetter(List<Team> teams, String filePath) throws Exception {
        Map<String, Team> teamMap = new HashMap<>();
        List<Team> newTeamList = new ArrayList<>();

        for (Team team : teams) {
            teamMap.put(team.getOwnerFirstName(), team);
        }

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line; // CSV line
            while ((line = reader.readNext()) != null) {
                if (line.length >= 2) {
                    String firstName1 = line[0].trim();
                    String firstName2 = line[1].trim();
                    if (teamMap.containsKey(firstName1) && teamMap.containsKey(firstName2)) {
                        Team team1 = teamMap.get(firstName1);
                        Team team2 = teamMap.get(firstName2);
                        newTeamList.add(team1);
                        newTeamList.add(team2);
                        team1.setRival(team2);
                        team2.setRival(team1);
                    }
                }
            }
        }

        return newTeamList;
    }

    public static List<Team> assembleLeagueTeams(List<Team> teams, boolean rivalPriority) {
        try {
            teams = TeamAssembler.parseTeams(TeamAssembler.class.getClassLoader().getResource("Teams.csv").getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rivalPriority) {
            try {
                teams = TeamAssembler.rivalSetter(teams, TeamAssembler.class.getClassLoader().getResource("Rivals.csv").getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return teams;
    }
}
