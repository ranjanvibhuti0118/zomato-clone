package com.zomato.payments.mapper;

import com.zomato.payments.dto.PaymentMethodDTO;
import com.zomato.payments.entity.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodToDTO {


    public PaymentMethodDTO mapToDTO(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("PaymentMethod cannot be null");
        }

        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setId(paymentMethod.getId());
        dto.setName(paymentMethod.getName());
        dto.setType(paymentMethod.getType());
        dto.setApiUrl(paymentMethod.getApiUrl());
        dto.setProvider(paymentMethod.getProvider());
        dto.setSupportsRefund(paymentMethod.getSupportsRefund());
        dto.setCreatedOn(paymentMethod.getCreatedOn());
        dto.setUpdatedOn(paymentMethod.getUpdatedOn());
        dto.setTransactionFee(paymentMethod.getTransactionFee());
        return dto;
    }
}
