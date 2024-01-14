package com.example.demo.service;

import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findById(int paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new RuntimeException("Payment not found")
        );
    }

    public void updatePaymentStatus(int paymentId, String status) {
        Payment payment = findById(paymentId);
        payment.setStatus(status);
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public List<Payment> findByPrescriptionId(int prescriptionId) {
        // Return a list of payments for a prescription ID
        return paymentRepository.findByPrescriptionId(prescriptionId);
    }

    // Additional methods as needed
}
