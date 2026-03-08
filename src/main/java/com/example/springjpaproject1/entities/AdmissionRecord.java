package com.example.springjpaproject1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="admission_record")
public class AdmissionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fees;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private StudentEntities studentEntities;
}
