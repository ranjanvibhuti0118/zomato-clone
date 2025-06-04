package com.zomato.payments.repository;

import com.zomato.payments.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentMethod, Long> {
}
