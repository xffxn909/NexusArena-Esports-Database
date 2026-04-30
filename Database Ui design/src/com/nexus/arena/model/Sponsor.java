package com.nexus.arena.model;

public class Sponsor {
    private int sponsorId;
    private String sponsorName;
    private String industry;
    private String contactEmail;

    public Sponsor() {}

    public Sponsor(int sponsorId, String sponsorName, String industry, String contactEmail) {
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
        this.industry = industry;
        this.contactEmail = contactEmail;
    }

    public int getSponsorId() { return sponsorId; }
    public void setSponsorId(int sponsorId) { this.sponsorId = sponsorId; }
    public String getSponsorName() { return sponsorName; }
    public void setSponsorName(String sponsorName) { this.sponsorName = sponsorName; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    @Override
    public String toString() { return sponsorName; }
}
