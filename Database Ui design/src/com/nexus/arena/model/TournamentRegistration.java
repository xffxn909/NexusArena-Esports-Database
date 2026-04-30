package com.nexus.arena.model;

import java.time.LocalDate;

public class TournamentRegistration {
    private int registrationId;
    private int tournamentId;
    private int teamId;
    private LocalDate registrationDate;
    private String status;

    public TournamentRegistration() {}

    public TournamentRegistration(int registrationId, int tournamentId, int teamId,
                                  LocalDate registrationDate, String status) {
        this.registrationId = registrationId;
        this.tournamentId = tournamentId;
        this.teamId = teamId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }
    public int getTournamentId() { return tournamentId; }
    public void setTournamentId(int tournamentId) { this.tournamentId = tournamentId; }
    public int getTeamId() { return teamId; }
    public void setTeamId(int teamId) { this.teamId = teamId; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() { return "Reg#" + registrationId + " T:" + tournamentId + " Team:" + teamId; }
}
