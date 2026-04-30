package com.nexus.arena.model;

import java.math.BigDecimal;

public class PrizeDistribution {
    private int prizeId;
    private int tournamentId;
    private int teamId;
    private int position;
    private BigDecimal prizeAmount;

    public PrizeDistribution() {}

    public PrizeDistribution(int prizeId, int tournamentId, int teamId,
                             int position, BigDecimal prizeAmount) {
        this.prizeId = prizeId;
        this.tournamentId = tournamentId;
        this.teamId = teamId;
        this.position = position;
        this.prizeAmount = prizeAmount;
    }

    public int getPrizeId() { return prizeId; }
    public void setPrizeId(int prizeId) { this.prizeId = prizeId; }
    public int getTournamentId() { return tournamentId; }
    public void setTournamentId(int tournamentId) { this.tournamentId = tournamentId; }
    public int getTeamId() { return teamId; }
    public void setTeamId(int teamId) { this.teamId = teamId; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public BigDecimal getPrizeAmount() { return prizeAmount; }
    public void setPrizeAmount(BigDecimal prizeAmount) { this.prizeAmount = prizeAmount; }

    @Override
    public String toString() { return "#" + position + " → $" + prizeAmount; }
}
