package com.familybank.controllers;

import com.familybank.models.PaymentRequest;
import com.familybank.models.StudentValidationResponse;
import com.familybank.services.FeePaymentNotificationService;
import com.familybank.services.StudentValidationService;
import com.familybank.utils.transactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/api/payments")
public class PaymentController {
    private final FeePaymentNotificationService feePaymentNotificationService;
    private final transactionResponse transactionResponse;
    private final StudentValidationService studentValidationService;
    private final StudentValidationResponse studentValidationResponse;
    private final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    public PaymentController(FeePaymentNotificationService feePaymentNotificationService,
                             transactionResponse transactionResponse, StudentValidationService studentValidationService,
                             StudentValidationResponse studentValidationResponse) {
        this.feePaymentNotificationService = feePaymentNotificationService;
        this.transactionResponse = transactionResponse;
        this.studentValidationService = studentValidationService;
        this.studentValidationResponse = studentValidationResponse;
    }

    @PostMapping("/fee-collection")
    public ResponseEntity<HashMap<Object, Object>> validatePayment(@RequestBody PaymentRequest paymentRequest) throws JsonProcessingException {
        // Process the payment request
        String studentId = String.valueOf(paymentRequest.getStudentId());
        if (studentId.length() < 1) {

            return ResponseEntity.badRequest().body(transactionResponse.genericResponse("Bad Request", "Please check student ID"));
        }
        // Send validation request to Pe University via http call
        StudentValidationResponse studentValidationResponse = studentValidationService.sendStudentValidationRequest(paymentRequest);
        String studentIdResponse = studentValidationResponse.getStudentId();

        //check if validation was successful
        if(studentIdResponse == null || studentIdResponse.isEmpty()){
            String noRecordFound = "No student exist with the provided ID";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(transactionResponse.genericResponse("Validation Failed", noRecordFound));
        }
        else {
            LOGGER.info("===Payment Request {}", paymentRequest);

            /*Call for FeePayment and send notification to University */
            //insert to bank db
            HashMap<Object, Object> feePaymentResponse = feePaymentNotificationService.submitPayment(paymentRequest);
            //send the response
            if (feePaymentResponse == null) {
                String errorMessage = "An internal server error occurred.";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(transactionResponse.genericResponse("Failed", errorMessage));
            } else {
                // Send payment notification to entity[Pesapap University]
                HashMap<Object, Object> paymentNotificationResponse = feePaymentNotificationService.paymentNotification(paymentRequest);
                return ResponseEntity.ok(paymentNotificationResponse);
            }
        }
    }

}
