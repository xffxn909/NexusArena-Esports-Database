package com.nexus.arena.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sponsorship {
    private int sponsorshipId;
    private int sponsorId;
    private int tournamentId;
    private BigDecimal amountContributed;
    private LocalDate contractStart;
    private LocalDate contractEnd;

    public Sponsorship() {}

    public Sponsorship(int sponsorshipId, int sponsorId, int tournamentId,
                       BigDecimal amountContributed, LocalDate contractStart, LocalDate contractEnd) {
        this.sponsorshipId = sponsorshipId;
        this.sponsorId = sponsorId;
        this.tournamentId = tournamentId;
        this.amountContributed = amountContributed;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
    }

    public int getSponsorshipId() { return sponsorshipId; }
    public void setSponsorshipId(int sponsorshipId) { this.sponsorshipId = sponsorshipId; }
    public int getSponsorId() { return sponsorId; }
    public void setSponsorId(int sponsorId) { this.sponsorId = sponsorId; }
    public int getTournamentId() { return tournamentId; }
    public void setTournamentId(int tournamentId) { this.tournamentId = tournamentId; }
    public BigDecimal getAmountContributed() { return amountContributed; }
    public void setAmountContributed(BigDecimal amountContributed) { this.amountContributed = amountContributed; }
    public LocalDate getContractStart() { return contractStart; }
    public void setContractStart(LocalDate contractStart) { this.contractStart = contractStart; }
    public LocalDate getContractEnd() { return contractEnd; }
    public void setContractEnd(LocalDate contractEnd) { this.contractEnd = contractEnd; }

    @Override
    public String toString() { return "Sponsorship#" + sponsorshipId + " $" + amountContributed; }
}
