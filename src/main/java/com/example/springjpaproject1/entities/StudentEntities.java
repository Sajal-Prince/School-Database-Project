package com.example.springjpaproject1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.html.HTMLIsIndexElement;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<ProfessorEntities> professorEntities;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<SubjectEntities> subjectEntities;

    public void addSubjects(SubjectEntities subjectEntities){
        this.subjectEntities.add(subjectEntities);
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
