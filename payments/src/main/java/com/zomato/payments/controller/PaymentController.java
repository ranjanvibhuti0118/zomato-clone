package com.zomato.payments.controller;

import com.zomato.payments.dto.PaymentMethodDTO;
import com.zomato.payments.entity.PaymentMethod;
import com.zomato.payments.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentMethodDTO> addNewPayment(@RequestBody PaymentMethod paymentMethod) {
        if (paymentMethod == null || paymentMethod.getName() == null || paymentMethod.getType() == null) {
            return ResponseEntity.badRequest().build();
        }
        PaymentMethodDTO paymentMethodDTO = paymentService.onBoardPaymentMethod(paymentMethod);
        if (paymentMethodDTO == null) {
            return ResponseEntity.status(500).build();
        }else {
            return ResponseEntity.ok(paymentMethodDTO);
        }

    }
}
