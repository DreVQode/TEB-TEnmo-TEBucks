package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    public Long transferId;
    public Long transferTypeId;
    public Long transferStatusId;
    public Long accountFrom;
    public Long accountTo;
    public Double transferAmount;


    public Transfer(Long transferId, Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, Double transferAmount) {
        this.transferId = transferId;
        this.transferTypeId =transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.transferAmount = transferAmount;
    }

    public Transfer() {
    }

    public Transfer(int userId, BigDecimal transferAmount) {
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }


}
