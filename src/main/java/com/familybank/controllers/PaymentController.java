package com.familybank.controllers;

import com.familybank.models.PaymentRequest;
import com.familybank.models.StudentValidationResponse;
import com.familybank.services.FeePaymentNotificationService;
import com.familybank.services.StudentValidationService;
import com.familybank.utils.ValidationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/api/payment")
public class PaymentController {
    private final FeePaymentNotificationService feePaymentNotificationService;
    private final ValidationResponse validationResponse;
    private final StudentValidationService studentValidationService;
    private final StudentValidationResponse studentValidationResponse;
    private final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    public PaymentController( FeePaymentNotificationService feePaymentNotificationService,
                             ValidationResponse validationResponse, StudentValidationService studentValidationService,
                              StudentValidationResponse studentValidationResponse) {
        this.feePaymentNotificationService = feePaymentNotificationService;
        this.validationResponse = validationResponse;
        this.studentValidationService = studentValidationService;
        this.studentValidationResponse = studentValidationResponse;
    }

    @PostMapping("/validate")
    public ResponseEntity<HashMap<String,Object>> validatePayment(@RequestBody PaymentRequest paymentRequest) throws JsonProcessingException {
        // Process the payment request
        String studentId = String.valueOf(paymentRequest.getStudentId());
        if(studentId.length()<1) {

            return ResponseEntity.badRequest().body(validationResponse.genericResponse("Bad Request", "Please check student ID"));
        }
        // Send validation request to Pe University via http call
        StudentValidationResponse studentValidationResponse = studentValidationService.sendStudentValidationRequest(paymentRequest);
        String studentId1 = studentValidationResponse.getStudentId();

        /*Call for FeePayment and send notification to University */
        //insert to bank db



        LOGGER.info("===studentID{}", studentId1);





        return ResponseEntity.ok(validationResponse.genericResponse("Success","Fee payment was successful"));
    }


}
