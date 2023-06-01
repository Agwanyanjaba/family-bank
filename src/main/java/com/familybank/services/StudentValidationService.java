package com.familybank.services;

import com.familybank.models.PaymentRequest;
import com.familybank.models.Student;
import com.familybank.models.StudentValidationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

@Service
public class StudentValidationService {
    /*call the validation endpoint*/
    public StudentValidationResponse sendStudentValidationRequest(PaymentRequest paymentRequest){
        // Create an instance of WebClient
        WebClient client = WebClient.create();
        StudentValidationResponse studentValidationResponse = new StudentValidationResponse();

    // Define the request payload
        Student validationRequest = new Student(paymentRequest.getStudentId()); // Replace studentId with the actual student ID

    // Send the validation request
        client.post()
                .uri("http://localhost:9091/v1/api/student/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validationRequest)
                .retrieve()
                .bodyToMono(StudentValidationResponse.class)
                .subscribe(response -> {
                    studentValidationResponse.setStudentId(response.getStudentId());
                    studentValidationResponse.setFirstName(response.getFirstName());
                    studentValidationResponse.setLastName(response.getLastName());
                    studentValidationResponse.setEmail(response.getEmail());
                });
        return studentValidationResponse;
    }
}
