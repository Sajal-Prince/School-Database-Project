package com.example.springjpaproject1.DTO;

import com.example.springjpaproject1.entities.ProfessorEntities;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class SubjectDTO {
    @Nullable
    private Long id;
    private String name;
    private Long professorId;
    private List<Long> studentEntities;
}
