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
public class StudentServicesImpl implements StudentServices{
    private final AdmissionRepositories admissionRepositories;
    private final ProfessorRepositories professorRepositories;
    private final SubjectRepositories subjectRepositories;
    private final StudentRepositories studentRepositories;

    public StudentServicesImpl(AdmissionRepositories admissionRepositories, ProfessorRepositories professorRepositories, SubjectRepositories subjectRepositories, StudentRepositories studentRepositories){
        this.admissionRepositories = admissionRepositories;
        this.professorRepositories = professorRepositories;
        this.subjectRepositories = subjectRepositories;
        this.studentRepositories = studentRepositories;
    }

    @Transactional
    @Override
    public void addStudent(StudentDTO studentDTO){
        convertStudentDTOToEntities(studentDTO);
    }

    @Transactional
    @Override
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
    @Override
    public StudentDTO getStudentbyID(Long id){
        AdmissionRecord admissionRecord = admissionRepositories.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with the given id..."));

        return convertToStudentDTO(admissionRecord);
    }

    @Override
    public List<StudentDTO> getAllStudents(){
        return admissionRepositories.findAllWithFullDetails().stream().map(this::convertToStudentDTO).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public ResponseEntity<?> editStudentById(Long id, StudentDTO studentDTO) {

        StudentEntities oldStudent = admissionRepositories.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with that id not found")).getStudentEntities();

        if(studentDTO.getFees()!=null){
            admissionRepositories.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with that id not found")).setFees(studentDTO.getFees());
        }

        if(oldStudent.getSubjectEntities()!=null) {
            for (SubjectEntities subject : oldStudent.getSubjectEntities())
                subject.getStudentEntities().remove(oldStudent);
            oldStudent.getSubjectEntities().clear();
        }

        if(oldStudent.getProfessorEntities()!=null) {
            for (ProfessorEntities professor : oldStudent.getProfessorEntities())
                professor.getStudentEntities().remove(oldStudent);
            oldStudent.getProfessorEntities().clear();
        }


        oldStudent.setName(studentDTO.getName());
        if(studentDTO.getProfessorEntities()!=null){
            List<ProfessorEntities> professorEntities=professorRepositories.findAllById(studentDTO.getProfessorEntities());
            for(ProfessorEntities professor:professorEntities) {
                oldStudent.getProfessorEntities().add(professor);
                professor.getStudentEntities().add(oldStudent);
            }
        }

        if(studentDTO.getSubjectEntities()!=null){
            List<SubjectEntities> subjectEntities=subjectRepositories.findAllById(studentDTO.getSubjectEntities());
            for(SubjectEntities subject:subjectEntities) {
                oldStudent.getSubjectEntities().add(subject);
                subject.getStudentEntities().add(oldStudent);
            }
        }

        return ResponseEntity.ok("Student was edited");
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
