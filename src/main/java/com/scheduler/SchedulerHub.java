package com.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.scheduler.config.LeagueConfig;
import com.scheduler.domain.Schedule;
import com.scheduler.domain.Team;
import com.scheduler.domain.Week;

public class SchedulerHub {
    public static void main(String[] args) {
        LeagueConfig leagueConfig = LeagueConfig.getInstance(); // Singleton
        List<Team> teams = new ArrayList<>();
        
        teams = TeamAssembler.assembleLeagueTeams(teams, leagueConfig.getRivalPriority());
        Schedule schedule = new Schedule(leagueConfig.getNumberOfWeeks(), teams);

        if (leagueConfig.getRivalPriority()) {
            Scheduler.rivalWeekSetter(schedule);
        }

        Scheduler.roundRobinSchedule(schedule);
        Scheduler.remainderMatchups(schedule);

        for (Week week : schedule.getWeeks()) {
            week.printWeek();
        }

        Scheduler.printSchedule(schedule);
    }
}