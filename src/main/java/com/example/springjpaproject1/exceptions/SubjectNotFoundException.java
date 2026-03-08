package com.example.springjpaproject1.exceptions;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(String message) {
        super(message);
    }
}
