package com.nexus.arena.model;

import java.time.LocalDate;

public class Roster {
    private int rosterId;
    private int playerId;
    private int teamId;
    private int gameId;
    private LocalDate joinDate;
    private String role;

    public Roster() {}

    public Roster(int rosterId, int playerId, int teamId, int gameId,
                  LocalDate joinDate, String role) {
        this.rosterId = rosterId;
        this.playerId = playerId;
        this.teamId = teamId;
        this.gameId = gameId;
        this.joinDate = joinDate;
        this.role = role;
    }

    public int getRosterId() { return rosterId; }
    public void setRosterId(int rosterId) { this.rosterId = rosterId; }
    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }
    public int getTeamId() { return teamId; }
    public void setTeamId(int teamId) { this.teamId = teamId; }
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() { return "Roster#" + rosterId + " P:" + playerId + " T:" + teamId; }
}
