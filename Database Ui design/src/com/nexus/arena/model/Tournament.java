package com.nexus.arena.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Tournament {
    private int tournamentId;
    private String tournamentName;
    private int gameId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String format;
    private BigDecimal totalPrizePool;

    public Tournament() {}

    public Tournament(int tournamentId, String tournamentName, int gameId,
                      LocalDate startDate, LocalDate endDate, String location,
                      String format, BigDecimal totalPrizePool) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.gameId = gameId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.format = format;
        this.totalPrizePool = totalPrizePool;
    }

    public int getTournamentId() { return tournamentId; }
    public void setTournamentId(int tournamentId) { this.tournamentId = tournamentId; }
    public String getTournamentName() { return tournamentName; }
    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public BigDecimal getTotalPrizePool() { return totalPrizePool; }
    public void setTotalPrizePool(BigDecimal totalPrizePool) { this.totalPrizePool = totalPrizePool; }

    @Override
    public String toString() { return tournamentName; }
}
