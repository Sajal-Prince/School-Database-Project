package com.example.springjpaproject1.services;

import com.example.springjpaproject1.DTO.ProfessorDTO;
import com.example.springjpaproject1.entities.ProfessorEntities;
import com.example.springjpaproject1.entities.StudentEntities;
import com.example.springjpaproject1.entities.SubjectEntities;
import com.example.springjpaproject1.exceptions.ProfessorNotFoundException;
import com.example.springjpaproject1.exceptions.StudentNotFoundException;
import com.example.springjpaproject1.exceptions.SubjectNotFoundException;
import com.example.springjpaproject1.repositories.AdmissionRepositories;
import com.example.springjpaproject1.repositories.ProfessorRepositories;
import com.example.springjpaproject1.repositories.SubjectRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServices {
    private final ProfessorRepositories professorRepositories;
    private final AdmissionRepositories admissionRepositories;
    private final SubjectRepositories subjectRepositories;


    public ProfessorServices(ProfessorRepositories professorRepositories, AdmissionRepositories admissionRepositories, SubjectRepositories subjectRepositories){
        this.professorRepositories = professorRepositories;
        this.admissionRepositories = admissionRepositories;
        this.subjectRepositories = subjectRepositories;
    }

    @Transactional
    public List<ProfessorDTO> getProfessors(){
        return professorRepositories.findAllOptimized().stream().map(this::convertPEntitityToPDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public ResponseEntity<?> addProfessor(ProfessorDTO professorDTO) {
        ProfessorEntities professorEntities = convertProfDTOtoProfessorEntities(professorDTO);
        if(professorDTO.getStudentEntities()!=null) {
            for (Long studentIds : professorDTO.getStudentEntities()) {
                StudentEntities student = admissionRepositories.findById(studentIds).orElseThrow(() -> new StudentNotFoundException("Student with that id not found")).getStudentEntities();
                student.addProfessor(professorEntities);
            }
        }

        if(professorDTO.getSubjectEntities()!=null){
            for(Long subjectIds:professorDTO.getSubjectEntities()){
                SubjectEntities subjectEntities = subjectRepositories.findById(subjectIds).orElseThrow(() -> new StudentNotFoundException("Student not found with that id"));
                subjectEntities.addProfessor(professorEntities);
            }
        }

        professorRepositories.save(professorEntities);

        return ResponseEntity.ok("The professor was saved my g");
    }

    @Transactional
    public ResponseEntity<?> deleteProfessorById(Long id){
        ProfessorEntities professorEntities = professorRepositories.findById(id).orElseThrow(() -> new ProfessorNotFoundException("Professor with that id not found"));
        if (professorEntities.getStudentEntities()!=null) {
            for (StudentEntities student : professorEntities.getStudentEntities())
                student.getProfessorEntities().remove(professorEntities);
            professorEntities.setStudentEntities(null);
        }
        if(professorEntities.getSubjectEntities()!=null) {
            for (SubjectEntities subject : professorEntities.getSubjectEntities())
                subject.setProfessor(null);
            professorEntities.setSubjectEntities(null);
        }
        professorRepositories.delete(professorEntities);
        return ResponseEntity.ok("Professor was deleted...");
    }

    @Transactional
    public ProfessorDTO getProfessorEntitityById(Long id){
        return convertPEntitityToPDTO(professorRepositories.findById(id).orElseThrow(() -> new ProfessorNotFoundException("Professor with that id not found")));
    }

    @Transactional
    public ProfessorEntities convertProfDTOtoProfessorEntities(ProfessorDTO professorDTO){
        ProfessorEntities professorEntities = new ProfessorEntities();
        professorEntities.setName(professorDTO.getName());
        if(professorDTO.getStudentEntities() != null)
            professorEntities.setStudentEntities(professorDTO
                            .getStudentEntities()
                            .stream()
                            .map(id -> admissionRepositories
                                    .findById(id)
                                    .orElseThrow(() -> new StudentNotFoundException("Student with that id not found"))
                                    .getStudentEntities())
                            .collect(Collectors.toCollection(HashSet::new)));
        if(professorDTO.getSubjectEntities() != null)
            professorEntities.setSubjectEntities(professorDTO
                    .getSubjectEntities()
                    .stream()
                    .map(id -> subjectRepositories
                            .findById(id)
                            .orElseThrow(() -> new SubjectNotFoundException("Subject with that id not found")))
                    .collect(Collectors.toCollection(HashSet::new)));
        return professorEntities;
    }

    @Transactional
    public ProfessorDTO convertPEntitityToPDTO(ProfessorEntities professorEntities){
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professorEntities.getId());
        professorDTO.setName(professorEntities.getName());
        professorDTO.setStudentEntities(professorEntities.getStudentEntities().stream().map(StudentEntities::getId).collect(Collectors.toCollection(ArrayList::new)));
        professorDTO.setSubjectEntities(professorEntities.getSubjectEntities().stream().map(SubjectEntities::getId).collect(Collectors.toCollection(ArrayList::new)));
        return professorDTO;
    }
}
