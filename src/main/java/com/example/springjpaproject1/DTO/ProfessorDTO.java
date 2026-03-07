package com.example.springjpaproject1.DTO;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class ProfessorDTO {
    @Nullable
    private Long id;
    private String name;
    private List<Long> studentEntities;
    private List<Long> subjectEntities;
}
