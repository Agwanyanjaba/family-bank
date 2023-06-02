package com.familybank.services;

import com.familybank.controllers.PaymentController;
import com.familybank.models.PaymentRequest;
import com.familybank.models.Student;
import com.familybank.models.StudentValidationResponse;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class StudentValidationService {
    private final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    /*call the validation endpoint*/

    public StudentValidationResponse sendStudentValidationRequest1(PaymentRequest paymentRequest) throws JsonProcessingException {
        // Create an instance of WebClient
        WebClient client = WebClient.create("http://localhost:9091");
        StudentValidationResponse studentValidationResponse = new StudentValidationResponse();
        Student validationRequest = new Student(paymentRequest.getStudentId());

        // Serialize validationRequest to JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonRequest = mapper.writeValueAsString(validationRequest);

        // Send the validation request
        client.post()
                .uri("/v1/api/student/validate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {})
                .subscribe(responseMap -> {
                    // Extract the values from the response map
                    HashMap<String, Object> responseBody = (HashMap<String, Object>) responseMap.get("ResponseBody");
                    String studentId = String.valueOf(responseBody.get("studentId"));
                    String firstName = (String) responseBody.get("firstName");
                    String lastName = (String) responseBody.get("lastName");
                    String email = (String) responseBody.get("email");

                    LOGGER.info("=== RES"+email);
                    // Set the values in studentValidationResponse
                    studentValidationResponse.setStudentId(studentId);
                    studentValidationResponse.setFirstName(firstName);
                    studentValidationResponse.setLastName(lastName);
                    studentValidationResponse.setEmail(email);


                    // Check if studentValidationResponse has the set values
                    if (studentValidationResponse.getStudentId() != null
                            && !studentValidationResponse.getStudentId().isEmpty()
                            && studentValidationResponse.getFirstName() != null
                            && !studentValidationResponse.getFirstName().isEmpty()
                            && studentValidationResponse.getLastName() != null
                            && !studentValidationResponse.getLastName().isEmpty()
                            && studentValidationResponse.getEmail() != null
                            && !studentValidationResponse.getEmail().isEmpty()) {
                        // Student validation response is complete
                        LOGGER.info("Student validation response is complete");
                    } else {
                        // Student validation response is incomplete
                        LOGGER.info("Student validation response is incomplete");
                    }
                });


        LOGGER.info("=== RES Final"+studentValidationResponse.getEmail());
        return studentValidationResponse;
    }
    public StudentValidationResponse sendStudentValidationRequest(PaymentRequest paymentRequest) throws JsonProcessingException {
        WebClient client = WebClient.create("http://localhost:9091");
        StudentValidationResponse studentValidationResponse = new StudentValidationResponse();
        Student validationRequest = new Student(paymentRequest.getStudentId());

        ObjectMapper mapper = new ObjectMapper();
        String jsonRequest = mapper.writeValueAsString(validationRequest);

        HashMap<String, Object> responseMap = client.post()
                .uri("/v1/api/student/validate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {})
                .block();

        if (responseMap != null) {
            HashMap<String, Object> responseBody = (HashMap<String, Object>) responseMap.get("ResponseBody");
            if (responseBody != null) {
                String studentId = String.valueOf(responseBody.get("studentId"));
                String firstName = (String) responseBody.get("firstName");
                String lastName = (String) responseBody.get("lastName");
                String email = (String) responseBody.get("email");
                LOGGER.info("=== RES"+email);

                studentValidationResponse.setStudentId(studentId);
                studentValidationResponse.setFirstName(firstName);
                studentValidationResponse.setLastName(lastName);
                studentValidationResponse.setEmail(email);
            }
        }

        // Check if studentValidationResponse has the set values
        if (studentValidationResponse.getStudentId() != null
                && !studentValidationResponse.getStudentId().isEmpty()
                && studentValidationResponse.getFirstName() != null
                && !studentValidationResponse.getFirstName().isEmpty()
                && studentValidationResponse.getLastName() != null
                && !studentValidationResponse.getLastName().isEmpty()
                && studentValidationResponse.getEmail() != null
                && !studentValidationResponse.getEmail().isEmpty()) {
            // Student validation response is complete
            LOGGER.info("Student validation response is complete");
        } else {
            // Student validation response is incomplete
            LOGGER.info("Student validation response is incomplete");
        }

        LOGGER.info("=== RES Final"+studentValidationResponse.getEmail());
        return studentValidationResponse;
    }


}
