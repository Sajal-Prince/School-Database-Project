package com.example.springjpaproject1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "professor")
public class ProfessorEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String Name;

    @ManyToMany(mappedBy = "professorEntities") //Inverse Side
    private List<StudentEntities> studentEntities;


    @OneToMany(mappedBy = "professor",cascade = {CascadeType.PERSIST, CascadeType.MERGE})  //Inverse side
    private List<SubjectEntities> subjectEntities;

    public void addSubject(SubjectEntities subjectEntities){
        this.subjectEntities.add(subjectEntities);
        subjectEntities.setProfessor(this);
    }
}
