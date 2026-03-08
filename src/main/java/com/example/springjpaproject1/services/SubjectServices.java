package com.example.springjpaproject1.services;

import com.example.springjpaproject1.DTO.SubjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectServices {
    List<SubjectDTO> getSubjects();

    SubjectDTO getSubjectById(Long id);

    @Transactional
    ResponseEntity<?> addSubject(SubjectDTO subjectDTO);

    @Transactional
    ResponseEntity<?> deleteSubjectById(Long id);

    ResponseEntity<?> editSubjectEntity(Long id, SubjectDTO subjectDTO);
}
