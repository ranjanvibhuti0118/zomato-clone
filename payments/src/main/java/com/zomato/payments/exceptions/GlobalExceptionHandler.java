package com.zomato.payments.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public String handleInvalidPaymentMethodDetails(InvalidPaymentMethodDetails ex) {
        // Log the exception or perform any other necessary actions
        return "Error: " + ex.getMessage(); // This can be customized to return a specific error response
    }
}
