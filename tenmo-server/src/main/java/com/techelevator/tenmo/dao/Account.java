package com.techelevator.tenmo.dao;

public class Account {

    private Long accountId;
    private Long userId;
    private double accountBalance;

    public Account(Long accountId, Long userId, double accountBalance) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountBalance = accountBalance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
