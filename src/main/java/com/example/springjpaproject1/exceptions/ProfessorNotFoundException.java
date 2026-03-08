package com.example.springjpaproject1.exceptions;

public class ProfessorNotFoundException extends RuntimeException{
    public ProfessorNotFoundException(String message) {
        super(message);
    }
}
