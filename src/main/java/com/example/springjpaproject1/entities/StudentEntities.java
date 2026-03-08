package com.example.springjpaproject1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "student")
public class StudentEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<ProfessorEntities> professorEntities;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<SubjectEntities> subjectEntities;

    public void addSubjects(SubjectEntities subjectEntities){
        if(this.subjectEntities == null)
            this.subjectEntities = new HashSet<>() {
        };
        this.subjectEntities.add(subjectEntities);

        if(subjectEntities.getStudentEntities()==null)
            subjectEntities.setStudentEntities(new ArrayList<>());
        subjectEntities.getStudentEntities().add(this);
    }

    public void addProfessor(ProfessorEntities professorEntities){
        if(this.professorEntities == null)
            this.professorEntities = new HashSet<>();
        this.professorEntities.add(professorEntities);

        if(professorEntities.getStudentEntities()==null)
            professorEntities.setStudentEntities(new HashSet<>());
        professorEntities.getStudentEntities().add(this);
    }
}
