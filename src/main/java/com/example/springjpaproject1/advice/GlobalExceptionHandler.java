package com.example.springjpaproject1.advice;

import com.example.springjpaproject1.exceptions.ProfessorNotFoundException;
import com.example.springjpaproject1.exceptions.StudentNotFoundException;
import com.example.springjpaproject1.exceptions.SubjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentNotFoundException(StudentNotFoundException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfessorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlerProfessorNotFoundException(ProfessorNotFoundException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlerSubjectNotFoundException(SubjectNotFoundException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.NOT_FOUND);
    }
}
