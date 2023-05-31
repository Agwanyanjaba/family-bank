package com.familybank.controllers;

import com.familybank.models.Payment;
import com.familybank.services.PeUniversityService;
import com.familybank.utils.ValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PeUniversityService peUniversityService;
    private final ValidationResponse validationResponse;

    public PaymentController(KafkaTemplate<String, String> kafkaTemplate, PeUniversityService peUniversityService,
                             ValidationResponse validationResponse) {
        this.kafkaTemplate = kafkaTemplate;
        this.peUniversityService = peUniversityService;
        this.validationResponse = validationResponse;
    }

    @PostMapping("/validate")
    public ResponseEntity<HashMap<String,Object>> validatePayment(@RequestBody Payment paymentRequest) {
        // Process the payment request
        String studentId = String.valueOf(paymentRequest.getStudentId());
        if(studentId.length()<1) {

            return ResponseEntity.badRequest().body(validationResponse.genericResponse("Bad Request", "Please check student ID"));
        }

        // Send validation request to Pe University via Kafka
        kafkaTemplate.send("payment-validation", String.valueOf(paymentRequest));

        return ResponseEntity.ok(validationResponse.genericResponse("Success","Fee payment was successful"));
    }


    // Validate student details in Pe University
    //boolean isValid = peUniversityService.validateStudentDetails(paymentDetails.getStudentId());

//        if (isValid) {
//            // Store payment details in Fam Bank
//            famBankService.storePaymentDetails(paymentDetails);
//
//            // Send payment notification via Kafka
//            String notificationMessage = "Payment received for student: " + paymentDetails.getStudentId();
//            kafkaTemplate.send("payment-notifications", notificationMessage);
//
//            // Optionally, add notification details to Pe University database
//            peUniversityService.saveNotification(paymentDetails.getStudentId(), notificationMessage);
//
//            return ResponseEntity.ok("Payment validated and processed successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid student details. Payment rejected.");
//        }

}
