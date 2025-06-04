package com.zomato.payments.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class PaymentMethodDTO {
    private Long id;
    private String name;
    private String provider;
    private String apiUrl;
    private PaymentType type;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Boolean supportsRefund;
    private BigDecimal transactionFee;

    public PaymentMethodDTO() {
    }
    public enum PaymentType {
        CREDIT_CARD, DEBIT_CARD, NET_BANKING, UPI, WALLET, CASH_ON_DELIVERY
    }


    public PaymentMethodDTO(Long id, String name, String provider, String apiUrl, PaymentType type,
                            LocalDateTime createdOn, LocalDateTime updatedOn, Boolean supportsRefund, BigDecimal transactionFee) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.apiUrl = apiUrl;
        this.type = type;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.supportsRefund = supportsRefund;
        this.transactionFee = transactionFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getSupportsRefund() {
        return supportsRefund;
    }

    public void setSupportsRefund(Boolean supportsRefund) {
        this.supportsRefund = supportsRefund;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }
}
