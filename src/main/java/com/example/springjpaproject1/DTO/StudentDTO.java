package com.example.springjpaproject1.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    @Nullable
    private Long id;
    private String name;
    private List<Long> professorEntities;
    private List<Long> subjectEntities;
    private Integer fees;
}
