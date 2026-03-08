package com.example.springjpaproject1.services;

import com.example.springjpaproject1.DTO.StudentDTO;
import com.example.springjpaproject1.entities.AdmissionRecord;
import com.example.springjpaproject1.entities.ProfessorEntities;
import com.example.springjpaproject1.entities.StudentEntities;
import com.example.springjpaproject1.entities.SubjectEntities;
import com.example.springjpaproject1.exceptions.StudentNotFoundException;
import com.example.springjpaproject1.repositories.AdmissionRepositories;
import com.example.springjpaproject1.repositories.ProfessorRepositories;
import com.example.springjpaproject1.repositories.StudentRepositories;
import com.example.springjpaproject1.repositories.SubjectRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServices {
    private final AdmissionRepositories admissionRepositories;
    private final ProfessorRepositories professorRepositories;
    private final SubjectRepositories subjectRepositories;
    private final StudentRepositories studentRepositories;

    public StudentServices(AdmissionRepositories admissionRepositories, ProfessorRepositories professorRepositories, SubjectRepositories subjectRepositories, StudentRepositories studentRepositories){
        this.admissionRepositories = admissionRepositories;
        this.professorRepositories = professorRepositories;
        this.subjectRepositories = subjectRepositories;
        this.studentRepositories = studentRepositories;
    }

    @Transactional
    public void addStudent(StudentDTO studentDTO){
        convertStudentDTOToEntities(studentDTO);
    }

    @Transactional
    public ResponseEntity<?> removeAdmission(Long id){
        AdmissionRecord admissionRecord = admissionRepositories.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with that id not found"));
        StudentEntities student = admissionRecord.getStudentEntities();
        if(student != null){
            student.getSubjectEntities().clear();
            student.getProfessorEntities().clear();
            studentRepositories.save(student);
        }
        admissionRepositories.delete(admissionRecord);
        return ResponseEntity.ok("The record was deleted");
    }

    @Transactional
    public StudentDTO getStudentbyID(Long id){
        AdmissionRecord admissionRecord = admissionRepositories.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with the given id..."));

        return convertToStudentDTO(admissionRecord);
    }

    public List<StudentDTO> getAllStudents(){
        return admissionRepositories.findAllWithFullDetails().stream().map(this::convertToStudentDTO).collect(Collectors.toList());
    }

    public StudentDTO convertToStudentDTO(AdmissionRecord admissionRecord){
        StudentDTO student = new StudentDTO();
        student.setId(admissionRecord.getId());
        student.setFees(admissionRecord.getFees());
        student.setName(admissionRecord.getStudentEntities().getName());
        student.setProfessorEntities(admissionRecord.getStudentEntities()
                .getProfessorEntities()
                .stream()
                .map(ProfessorEntities::getId)
                .collect(Collectors
                        .toCollection(ArrayList::new)));
        student.setSubjectEntities(admissionRecord.getStudentEntities()
                .getSubjectEntities()
                .stream()
                .map(SubjectEntities::getId)
                .collect(Collectors
                        .toCollection(ArrayList::new)));


        return student;
    }

    @Transactional
    public void convertStudentDTOToEntities(StudentDTO studentDTO){
        AdmissionRecord admissionRecord = new AdmissionRecord();
        StudentEntities studentEntities = new StudentEntities();
        studentEntities.setName(studentDTO.getName());
        if(studentDTO.getSubjectEntities()!=null)
            studentEntities.setSubjectEntities(new HashSet<>(subjectRepositories.findAllById(studentDTO.getSubjectEntities())));
        if(studentDTO.getProfessorEntities()!=null)
            studentEntities.setProfessorEntities(new HashSet<>(professorRepositories.findAllById(studentDTO.getProfessorEntities())));

        studentRepositories.save(studentEntities);

        admissionRecord.setFees(studentDTO.getFees());
        admissionRecord.setStudentEntities(studentEntities);

        admissionRepositories.save(admissionRecord);
        ResponseEntity.ok("You data was saved");
    }

}
