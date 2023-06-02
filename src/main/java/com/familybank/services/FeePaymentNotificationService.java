package com.familybank.services;

import com.familybank.models.PaymentRequest;
import com.familybank.repositories.FeePaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class FeePaymentNotificationService {
    private final FeePaymentRepository feePaymentRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(FeePaymentNotificationService.class);

    @Autowired
    public FeePaymentNotificationService(FeePaymentRepository feePaymentRepository){
        this.feePaymentRepository = feePaymentRepository;
    }
    /*
    Logic to submit fee payment
    */
    public HashMap<Object, Object> submitPayment(PaymentRequest feePayment) {

        try {
            PaymentRequest savedPayment = feePaymentRepository.save(feePayment);
            //Save operation was successful
            HashMap<Object, Object> paymentResponse = new LinkedHashMap<>();
            paymentResponse.put("paymentId", savedPayment.getPaymentId());
            paymentResponse.put("studentId",savedPayment.getStudentId());
            paymentResponse.put("amountPaid", savedPayment.getAmount());
            paymentResponse.put("paymentDate", savedPayment.getPaymentDate());
            return paymentResponse;

        } catch (DataIntegrityViolationException e) {
            // Save operation failed due to constraints or validation errors
            LOGGER.error("===[ERROR on payment submission"+e.getMessage());
            return null;
        }
    }
    /*call the notification endpoint*/
    public HashMap<Object, Object> paymentNotification(PaymentRequest paymentRequest) throws JsonProcessingException {
        WebClient client = WebClient.create("http://localhost:9091");
        ObjectMapper mapper = new ObjectMapper();
        String jsonRequest = mapper.writeValueAsString(paymentRequest);

        HashMap<Object, Object> responseMap = client.post()
                .uri("/v1/api/student/notification")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<Object, Object>>() {})
                .block();
        if (responseMap != null) {
            HashMap<String, Object> responseBody = (HashMap<String, Object>) responseMap.get("ResponseBody");
            if (responseBody != null) {
                LOGGER.info("=== RESPONSE RECEIVED[ {}",responseBody);
            }
        }
        return responseMap;
    }

}
