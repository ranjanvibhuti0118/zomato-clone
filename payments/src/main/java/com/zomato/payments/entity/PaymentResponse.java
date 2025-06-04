package com.zomato.payments.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_responses")
public class PaymentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "provider")
    private String provider;

    private enum EventType {
        PAYMENT_SUCCESS, PAYMENT_FAILURE, REFUND_INITIATED, REFUND_COMPLETED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public PaymentResponse() {
    }

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
