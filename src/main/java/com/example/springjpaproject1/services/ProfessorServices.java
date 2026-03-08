package com.example.springjpaproject1.services;

import com.example.springjpaproject1.DTO.ProfessorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfessorServices {

    List<ProfessorDTO> getProfessors();

    ResponseEntity<?> deleteProfessorById(Long id);

    ProfessorDTO getProfessorEntitityById(Long id);

    ResponseEntity<?> addProfessor(ProfessorDTO professorDTO);

    ResponseEntity<?> editProfessorById(Long id, ProfessorDTO professorDTO);
}
