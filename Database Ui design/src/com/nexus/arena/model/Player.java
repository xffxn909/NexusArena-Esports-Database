package com.nexus.arena.model;

import java.time.LocalDate;

public class Player {
    private int playerId;
    private String username;
    private String realName;
    private String nationality;
    private String email;
    private String playerRank;
    private LocalDate dateOfBirth;

    public Player() {}

    public Player(int playerId, String username, String realName, String nationality,
                  String email, String playerRank, LocalDate dateOfBirth) {
        this.playerId = playerId;
        this.username = username;
        this.realName = realName;
        this.nationality = nationality;
        this.email = email;
        this.playerRank = playerRank;
        this.dateOfBirth = dateOfBirth;
    }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPlayerRank() { return playerRank; }
    public void setPlayerRank(String playerRank) { this.playerRank = playerRank; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public String toString() { return username + " (" + realName + ")"; }
}
