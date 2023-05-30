package com.familybank.controllers;

import com.familybank.services.PeUniversityService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PeUniversityService peUniversityService;

    public PaymentController(KafkaTemplate<String, String> kafkaTemplate, PeUniversityService peUniversityService) {
        this.kafkaTemplate = kafkaTemplate;
        this.peUniversityService = peUniversityService;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validatePayment(@RequestBody PaymentDetails paymentDetails) {
        // Validate student details in Pe University
        boolean isValid = peUniversityService.validateStudentDetails(paymentDetails.getStudentId());

        if (isValid) {
            // Store payment details in Fam Bank
            famBankService.storePaymentDetails(paymentDetails);

            // Send payment notification via Kafka
            String notificationMessage = "Payment received for student: " + paymentDetails.getStudentId();
            kafkaTemplate.send("payment-notifications", notificationMessage);

            // Optionally, add notification details to Pe University database
            peUniversityService.saveNotification(paymentDetails.getStudentId(), notificationMessage);

            return ResponseEntity.ok("Payment validated and processed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid student details. Payment rejected.");
        }
    }
}
