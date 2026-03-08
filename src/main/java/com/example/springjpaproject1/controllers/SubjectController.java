package com.example.springjpaproject1.controllers;

import com.example.springjpaproject1.DTO.SubjectDTO;
import com.example.springjpaproject1.services.SubjectServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {


    private final SubjectServices subjectServices;

    public SubjectController(SubjectServices subjectServices) {
        this.subjectServices = subjectServices;
    }

    @GetMapping("/get")
    public List<SubjectDTO> getSubjects(){
        return subjectServices.getSubjects();
    }

    @GetMapping("/{id}")
    public SubjectDTO getSubjectById(@PathVariable Long id){
        return subjectServices.getSubjectById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSubject(@RequestBody SubjectDTO subjectDTO){
        return subjectServices.addSubject(subjectDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id){
        return subjectServices.deleteSubjectById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editSubjectEntity(@PathVariable Long id,@RequestBody SubjectDTO subjectDTO){ return subjectServices.editSubjectEntity(id,subjectDTO); }

}
