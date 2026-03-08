package com.example.springjpaproject1.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


@Data
public class ProfessorDTO {
    @Nullable
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private List<Long> studentEntities;
    private List<Long> subjectEntities;
}
