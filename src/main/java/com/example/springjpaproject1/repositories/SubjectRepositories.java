package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.SubjectEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepositories extends JpaRepository<SubjectEntities,Long> {
}
