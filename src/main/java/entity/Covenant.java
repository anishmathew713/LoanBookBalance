package main.java.entity;

import java.math.BigDecimal;

public class Covenant {

    private Integer bankId;
    private Integer facilityId;
    private BigDecimal maxDefaultLikelihood;
    private String bannedState;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public BigDecimal getMaxDefaultLikelihood() {
        return maxDefaultLikelihood;
    }

    public void setMaxDefaultLikelihood(BigDecimal maxDefaultLikelihood) {
        this.maxDefaultLikelihood = maxDefaultLikelihood;
    }

    public String getBannedState() {
        return bannedState;
    }

    public void setBannedState(String bannedState) {
        this.bannedState = bannedState;
    }
}
