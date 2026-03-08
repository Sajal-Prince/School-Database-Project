package com.example.springjpaproject1.services;

import com.example.springjpaproject1.DTO.StudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentServices {
    @Transactional
    void addStudent(StudentDTO studentDTO);

    @Transactional
    ResponseEntity<?> removeAdmission(Long id);

    @Transactional
    StudentDTO getStudentbyID(Long id);

    List<StudentDTO> getAllStudents();

    ResponseEntity<?> editStudentById(Long id, StudentDTO studentDTO);
}
