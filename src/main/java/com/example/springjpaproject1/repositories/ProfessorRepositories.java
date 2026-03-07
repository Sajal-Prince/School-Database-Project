package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.ProfessorEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepositories extends JpaRepository<ProfessorEntities,Long> {
}
