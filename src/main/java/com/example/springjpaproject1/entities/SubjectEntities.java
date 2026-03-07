package com.example.springjpaproject1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.nio.MappedByteBuffer;
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

    @ManyToMany(mappedBy = "subjectEntities",cascade = CascadeType.MERGE)
    private List<StudentEntities> studentEntities;
}
