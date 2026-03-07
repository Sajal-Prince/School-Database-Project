package com.example.springjpaproject1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "subject")
public class SubjectEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntities professor;

    @ManyToMany(mappedBy = "subjectEntities")
    private List<StudentEntities> studentEntities;

    public void addProfessor(ProfessorEntities professorEntities){
        this.setProfessor(professorEntities);
        professorEntities.getSubjectEntities().add(this);
    }
}
