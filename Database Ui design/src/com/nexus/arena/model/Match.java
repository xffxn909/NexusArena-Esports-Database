package com.nexus.arena.model;

import java.time.LocalDateTime;

public class Match {
    private int matchId;
    private int tournamentId;
    private int team1Id;
    private int team2Id;
    private LocalDateTime matchDate;
    private String stage;
    private Integer winnerTeamId; // nullable
    private String score;

    public Match() {}

    public Match(int matchId, int tournamentId, int team1Id, int team2Id,
                 LocalDateTime matchDate, String stage, Integer winnerTeamId, String score) {
        this.matchId = matchId;
        this.tournamentId = tournamentId;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.matchDate = matchDate;
        this.stage = stage;
        this.winnerTeamId = winnerTeamId;
        this.score = score;
    }

    public int getMatchId() { return matchId; }
    public void setMatchId(int matchId) { this.matchId = matchId; }
    public int getTournamentId() { return tournamentId; }
    public void setTournamentId(int tournamentId) { this.tournamentId = tournamentId; }
    public int getTeam1Id() { return team1Id; }
    public void setTeam1Id(int team1Id) { this.team1Id = team1Id; }
    public int getTeam2Id() { return team2Id; }
    public void setTeam2Id(int team2Id) { this.team2Id = team2Id; }
    public LocalDateTime getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDateTime matchDate) { this.matchDate = matchDate; }
    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }
    public Integer getWinnerTeamId() { return winnerTeamId; }
    public void setWinnerTeamId(Integer winnerTeamId) { this.winnerTeamId = winnerTeamId; }
    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }

    @Override
    public String toString() { return "Match#" + matchId + " " + stage; }
}
