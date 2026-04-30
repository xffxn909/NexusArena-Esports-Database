package com.nexus.arena.model;

import java.time.LocalDate;

public class Team {
    private int teamId;
    private String teamName;
    private LocalDate foundedDate;
    private String coachName;
    private String country;

    public Team() {}

    public Team(int teamId, String teamName, LocalDate foundedDate, String coachName, String country) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.foundedDate = foundedDate;
        this.coachName = coachName;
        this.country = country;
    }

    public int getTeamId() { return teamId; }
    public void setTeamId(int teamId) { this.teamId = teamId; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public LocalDate getFoundedDate() { return foundedDate; }
    public void setFoundedDate(LocalDate foundedDate) { this.foundedDate = foundedDate; }
    public String getCoachName() { return coachName; }
    public void setCoachName(String coachName) { this.coachName = coachName; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() { return teamName; }
}
