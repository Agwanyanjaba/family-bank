package com.familybank.controllers;

import com.familybank.models.Payment;
import com.familybank.services.FeePaymentNotificationService;
import com.familybank.utils.ValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
    private final FeePaymentNotificationService feePaymentNotificationService;
    private final ValidationResponse validationResponse;

    public PaymentController( FeePaymentNotificationService feePaymentNotificationService,
                             ValidationResponse validationResponse) {
        this.feePaymentNotificationService = feePaymentNotificationService;
        this.validationResponse = validationResponse;
    }

    @PostMapping("/validate")
    public ResponseEntity<HashMap<String,Object>> validatePayment(@RequestBody Payment paymentRequest) {
        // Process the payment request
        String studentId = String.valueOf(paymentRequest.getStudentId());
        if(studentId.length()<1) {

            return ResponseEntity.badRequest().body(validationResponse.genericResponse("Bad Request", "Please check student ID"));
        }
        // Send validation request to Pe University via http call
        return ResponseEntity.ok(validationResponse.genericResponse("Success","Fee payment was successful"));
    }


}
