package com.zomato.payments.service;

import com.zomato.payments.dto.PaymentMethodDTO;
import com.zomato.payments.entity.PaymentMethod;
import com.zomato.payments.exceptions.InvalidPaymentMethodDetails;
import com.zomato.payments.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository ) {
    this.paymentRepository = paymentRepository;
    }

    public PaymentMethodDTO onBoardPaymentMethod(PaymentMethod paymentMethod) {

        if (paymentMethod == null || paymentMethod.getName() == null || paymentMethod.getType() == null) {
            throw new InvalidPaymentMethodDetails("Invalid payment method details");
        }

        PaymentMethod savedPaymentMethod = paymentRepository.save(paymentMethod);
        return new PaymentMethodDTO(
                savedPaymentMethod.getId(),
                savedPaymentMethod.getName(),
                savedPaymentMethod.getProvider(),
                savedPaymentMethod.getApiUrl(),
                savedPaymentMethod.getType(),
                savedPaymentMethod.getCreatedOn(),
                savedPaymentMethod.getUpdatedOn(),
                savedPaymentMethod.getSupportsRefund(),
                savedPaymentMethod.getTransactionFee()
        );
    }
}
