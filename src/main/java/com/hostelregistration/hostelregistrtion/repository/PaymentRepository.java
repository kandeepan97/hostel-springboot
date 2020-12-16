package com.hostelregistration.hostelregistrtion.repository;

import com.hostelregistration.hostelregistrtion.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {


}
