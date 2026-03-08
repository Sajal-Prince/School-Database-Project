package com.example.springjpaproject1.controllers;

import com.example.springjpaproject1.DTO.StudentDTO;
import com.example.springjpaproject1.services.StudentServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admission")
public class StudentController {
    private final StudentServices studentServices;
    public StudentController(StudentServices studentServices){
        this.studentServices = studentServices;
    }

    @PostMapping("/add")
    public void addStudent(@RequestBody StudentDTO studentDTO){
        studentServices.addStudent(studentDTO);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        return studentServices.removeAdmission(id);
    }

    @GetMapping("/student/{id}")
    public StudentDTO getStudentByID(@PathVariable Long id){
        return studentServices.getStudentbyID(id);
    }

    @GetMapping("/get")
    public List<StudentDTO> getAllStudents(){
        return studentServices.getAllStudents();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editStudentById(@PathVariable Long id,@RequestBody StudentDTO studentDTO){ return studentServices.editStudentById(id,studentDTO); }
}
