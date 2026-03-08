package com.example.springjpaproject1.services;


import com.example.springjpaproject1.DTO.SubjectDTO;
import com.example.springjpaproject1.entities.ProfessorEntities;
import com.example.springjpaproject1.entities.StudentEntities;
import com.example.springjpaproject1.entities.SubjectEntities;
import com.example.springjpaproject1.exceptions.ProfessorNotFoundException;
import com.example.springjpaproject1.exceptions.SubjectNotFoundException;
import com.example.springjpaproject1.repositories.ProfessorRepositories;
import com.example.springjpaproject1.repositories.StudentRepositories;
import com.example.springjpaproject1.repositories.SubjectRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServices {

    private final SubjectRepositories subjectRepositories;
    private final ProfessorRepositories professorRepositories;
    private final StudentRepositories studentRepositories;

    public SubjectServices(SubjectRepositories subjectRepositories, ProfessorRepositories professorRepositories, StudentRepositories studentRepositories) {
        this.subjectRepositories = subjectRepositories;
        this.professorRepositories = professorRepositories;
        this.studentRepositories = studentRepositories;
    }

    public List<SubjectDTO> getSubjects() {
        return subjectRepositories.findAllSubjectsFull().stream().map(this::convertSubEntityToSubDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    public SubjectDTO getSubjectById(Long id) {
        return convertSubEntityToSubDTO(subjectRepositories.findById(id).orElseThrow(() -> new SubjectNotFoundException("Subject with that id not found")));
    }

    @Transactional
    public ResponseEntity<?> addSubject(SubjectDTO subjectDTO){
        SubjectEntities subject = convertSubDTOtoSubEntities(subjectDTO);
        if(subject.getProfessor()!=null) {
            ProfessorEntities professorEntities = professorRepositories.findById(subjectDTO.getProfessorId()).orElseThrow(() -> new ProfessorNotFoundException("Professor with that id not found"));
            professorEntities.addSubject(subject);
        }

        if(subject.getStudentEntities()!=null) {
            List<StudentEntities> studentEntities = studentRepositories.findAllById(subjectDTO.getStudentEntities());
            for (StudentEntities students : studentEntities) {
                students.addSubjects(subject);
            }
        }

        subjectRepositories.save(subject);
        return ResponseEntity.ok("The Subject was added.");
    }

    @Transactional
    public ResponseEntity<?> deleteSubjectById(Long id) {
        SubjectEntities subjectEntities = subjectRepositories.findById(id).orElseThrow(() -> new SubjectNotFoundException("Subject with that id not found"));
        if(subjectEntities.getProfessor()!=null) {
            subjectEntities.getProfessor().getSubjectEntities().remove(subjectEntities);
            subjectEntities.setProfessor(null);
        }
        if(subjectEntities.getStudentEntities()!=null) {
            for (StudentEntities student : subjectEntities.getStudentEntities())
                student.getSubjectEntities().remove(subjectEntities);
            subjectEntities.setStudentEntities(null);
        }
        subjectRepositories.delete(subjectEntities);
        return ResponseEntity.ok("The subject was deleted");
    }

    public SubjectDTO convertSubEntityToSubDTO(SubjectEntities subjectEntities){
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subjectEntities.getId());
        subjectDTO.setName(subjectEntities.getName());
        if(subjectEntities.getProfessor() != null)
            subjectDTO.setProfessorId(subjectEntities.getProfessor().getId());
        if(subjectEntities.getStudentEntities() != null)
            subjectDTO.setStudentEntities(subjectEntities.getStudentEntities().stream().map(StudentEntities::getId).collect(Collectors.toCollection(ArrayList::new)));
        return subjectDTO;
    }

    public SubjectEntities convertSubDTOtoSubEntities(SubjectDTO subjectDTO){
        SubjectEntities subjectEntities = new SubjectEntities();
        subjectEntities.setId(subjectDTO.getId());
        subjectEntities.setName(subjectDTO.getName());
        if(subjectDTO.getProfessorId()!=null)
            subjectEntities.setProfessor(professorRepositories.findById(subjectDTO.getProfessorId()).orElseThrow(()->new ProfessorNotFoundException("Prfessor with that id not found")));
        if(subjectDTO.getStudentEntities()!=null)
            subjectEntities.setStudentEntities(studentRepositories.findAllById(subjectDTO.getStudentEntities()));
        return subjectEntities;
    }



}
