package com.example.springjpaproject1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<ProfessorEntities> professorEntities;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<SubjectEntities> subjectEntities;

    public void addSubjects(SubjectEntities subjectEntities){
        if(this.subjectEntities == null)
            this.subjectEntities = new ArrayList<>();
        this.subjectEntities.add(subjectEntities);

        if(subjectEntities.getStudentEntities()==null)
            subjectEntities.setStudentEntities(new ArrayList<>());
        subjectEntities.getStudentEntities().add(this);
    }

    public void addProfessor(ProfessorEntities professorEntities){
        if(this.professorEntities == null)
            this.professorEntities = new ArrayList<>();
        this.professorEntities.add(professorEntities);

        if(professorEntities.getStudentEntities()==null)
            professorEntities.setStudentEntities(new ArrayList<>());
        professorEntities.getStudentEntities().add(this);
    }
}
