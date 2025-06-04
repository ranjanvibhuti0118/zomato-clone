package com.zomato.payments.exceptions;

public class InvalidPaymentMethodDetails extends RuntimeException{
    public InvalidPaymentMethodDetails(String message) {
        super(message);
    }
}
