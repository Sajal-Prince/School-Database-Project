package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.SubjectEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepositories extends JpaRepository<SubjectEntities,Long> {

    @Query("SELECT DISTINCT s FROM SubjectEntities s " +
            "LEFT JOIN FETCH s.professor " +
            "LEFT JOIN FETCH s.studentEntities")
    List<SubjectEntities> findAllSubjectsFull();
}
