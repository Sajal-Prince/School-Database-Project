package com.example.springjpaproject1.services;


import com.example.springjpaproject1.DTO.SubjectDTO;
import com.example.springjpaproject1.entities.ProfessorEntities;
import com.example.springjpaproject1.entities.StudentEntities;
import com.example.springjpaproject1.entities.SubjectEntities;
import com.example.springjpaproject1.repositories.ProfessorRepositories;
import com.example.springjpaproject1.repositories.StudentRepositories;
import com.example.springjpaproject1.repositories.SubjectRepositories;
import org.apache.coyote.Response;
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
        return subjectRepositories.findAll().stream().map(this::convertSubEntityToSubDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    public SubjectDTO getSubjectById(Long id) {
        return convertSubEntityToSubDTO(subjectRepositories.findById(id).orElseThrow());
    }

    @Transactional
    public ResponseEntity<?> addSubject(SubjectDTO subjectDTO){
        SubjectEntities subject = convertSubDTOtoSubEntities(subjectDTO);
        ProfessorEntities professorEntities = professorRepositories.findById(subjectDTO.getProfessorId()).orElseThrow();
        professorEntities.addSubject(subject);

        List<StudentEntities> studentEntities = studentRepositories.findAllById(subjectDTO.getStudentEntities());
        for(StudentEntities students: studentEntities){
            students.addSubjects(subject);
        }
        subjectRepositories.save(subject);

        return ResponseEntity.ok("The Subject was added.");
    }

    @Transactional
    public ResponseEntity<?> deleteSubjectById(Long id) {
        SubjectEntities subjectEntities = subjectRepositories.findById(id).orElseThrow();
        if(subjectEntities.getProfessor()!=null)
            subjectEntities.setProfessor(null);
        if(subjectEntities.getStudentEntities()!=null)
            subjectEntities.setStudentEntities(null);
        subjectRepositories.delete(subjectEntities);
        return ResponseEntity.ok("The suject was deleted");
    }

    public SubjectDTO convertSubEntityToSubDTO(SubjectEntities subjectEntities){
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subjectEntities.getId());
        subjectDTO.setName(subjectEntities.getName());
        subjectDTO.setProfessorId(subjectEntities.getProfessor().getId());
        subjectDTO.setStudentEntities(subjectEntities.getStudentEntities().stream().map(StudentEntities::getId).collect(Collectors.toCollection(ArrayList::new)));
        return subjectDTO;
    }

    public SubjectEntities convertSubDTOtoSubEntities(SubjectDTO subjectDTO){
        SubjectEntities subjectEntities = new SubjectEntities();
        subjectEntities.setId(subjectDTO.getId());
        subjectEntities.setName(subjectDTO.getName());
        if(subjectDTO.getProfessorId()!=null)
            subjectEntities.setProfessor(professorRepositories.findById(subjectDTO.getProfessorId()).orElseThrow());
        if(subjectDTO.getStudentEntities()!=null)
            subjectEntities.setStudentEntities(studentRepositories.findAllById(subjectDTO.getStudentEntities()));
        return subjectEntities;
    }



}
