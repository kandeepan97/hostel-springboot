package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Payment;
import com.hostelregistration.hostelregistrtion.repository.PaymentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class PaymentController {
    private PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        super();
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/payments")
    Collection<Payment> payments(){

        return paymentRepository.findAll();
    }

}
