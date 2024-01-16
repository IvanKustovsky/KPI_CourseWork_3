package com.example.coursework.dto;

import java.util.Date;

public class Order {

    private final int commercialId;
    private final String bankDetails;
    private final String programName;
    private final double programRating;
    private final double programRate;
    private final Date airDate;
    private final int adDuration;
    private final double contractAmount;
    private final String agentDetails;

    public Order(int commercialId, String bankDetails, String programName, double programRating, double programRate,
                 Date airDate, int adDuration, double contractAmount, String agentDetails) {
        this.commercialId = commercialId;
        this.bankDetails = bankDetails;
        this.programName = programName;
        this.programRating = programRating;
        this.programRate = programRate;
        this.airDate = airDate;
        this.adDuration = adDuration;
        this.contractAmount = contractAmount;
        this.agentDetails = agentDetails;
    }
    public Order(int commercialId, String programName, double programRating, double programRate,
                 Date airDate, int adDuration, double contractAmount, String agentDetails) {
        this.commercialId = commercialId;
        this.programName = programName;
        this.programRating = programRating;
        this.programRate = programRate;
        this.airDate = airDate;
        this.adDuration = adDuration;
        this.contractAmount = contractAmount;
        this.agentDetails = agentDetails;
        this.bankDetails = null;
    }

    public int getCommercialId() {
        return commercialId;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public String getProgramName() {
        return programName;
    }

    public double getProgramRating() {
        return programRating;
    }

    public double getProgramRate() {
        return programRate;
    }

    public Date getAirDate() {
        return airDate;
    }

    public int getAdDuration() {
        return adDuration;
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public String getAgentDetails() {
        return agentDetails;
    }
}