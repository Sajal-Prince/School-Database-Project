package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.ProfessorEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepositories extends JpaRepository<ProfessorEntities,Long> {

    @Query( "SELECT DISTINCT p FROM ProfessorEntities p " +
            "LEFT JOIN FETCH p.studentEntities " +
            "LEFT JOIN FETCH p.subjectEntities ")
    List<ProfessorEntities> findAllOptimized();
}
