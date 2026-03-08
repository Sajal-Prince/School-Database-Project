package com.example.springjpaproject1.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class ApiErrorResponse {

    private LocalDateTime localDateTime = LocalDateTime.now();
    private String error;
    private HttpStatus statusCode;


    public ApiErrorResponse(String error, HttpStatus statusCode) {
        this.error = error;
        this.statusCode = statusCode;
    }
}
