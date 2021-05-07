package main.java.entity;

import java.math.BigDecimal;
import java.util.List;

public class Facility {

    private Integer facilityId;
    private Integer bankId;
    private BigDecimal interestRate;
    private BigDecimal amount;
    private BigDecimal maxDefaultLikelihood;
    private List<String> bannedState;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMaxDefaultLikelihood() {
        return maxDefaultLikelihood;
    }

    public void setMaxDefaultLikelihood(BigDecimal maxDefaultLikelihood) {
        this.maxDefaultLikelihood = maxDefaultLikelihood;
    }

    public List<String> getBannedState() {
        return bannedState;
    }

    public void setBannedState(List<String> bannedState) {
        this.bannedState = bannedState;
    }
}
