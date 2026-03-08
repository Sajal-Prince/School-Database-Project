package com.example.springjpaproject1.controllers;

import com.example.springjpaproject1.DTO.ProfessorDTO;
import com.example.springjpaproject1.services.ProfessorServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {
    ProfessorServices professorServices;
    public ProfessorController(ProfessorServices professorServices){
        this.professorServices=professorServices;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProfessors(@RequestBody @Valid ProfessorDTO professorDTO){
        return professorServices.addProfessor(professorDTO);
    }

    @GetMapping("/{id}")
    public ProfessorDTO getProfessorById(@PathVariable Long id){
        return professorServices.getProfessorEntitityById(id);
    }

    @GetMapping("/get")
    public List<ProfessorDTO> getProfessors(){
        return professorServices.getProfessors();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProfessorById(@PathVariable Long id){
        return professorServices.deleteProfessorById(id);
    }
}
