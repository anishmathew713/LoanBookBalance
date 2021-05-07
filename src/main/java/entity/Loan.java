package main.java.entity;

import java.math.BigDecimal;

public class Loan {

    private Integer loanId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal defaultLikelihood;
    private String state;
    private Boolean funded = false;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getDefaultLikelihood() {
        return defaultLikelihood;
    }

    public void setDefaultLikelihood(BigDecimal defaultLikelihood) {
        this.defaultLikelihood = defaultLikelihood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getFunded() {
        return funded;
    }

    public void setFunded(Boolean funded) {
        this.funded = funded;
    }
}
