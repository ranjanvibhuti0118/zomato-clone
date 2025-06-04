package com.zomato.payments.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
public class PaymentMethod {

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, String provider, String apiUrl, PaymentType type,
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "payment_method_name")
    private String name;

    private String provider;
    private String apiUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @ColumnDefault("false")
    private Boolean supportsRefund;

    @Column(name = "transaction_fee", precision = 10, scale = 2)
    @ColumnDefault("0.00")
    private BigDecimal transactionFee;

    public enum PaymentType {
        CREDIT_CARD, DEBIT_CARD, NET_BANKING, UPI, WALLET, CASH_ON_DELIVERY
    }

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
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

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
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