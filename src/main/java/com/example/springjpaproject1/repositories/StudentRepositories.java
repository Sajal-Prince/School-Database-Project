package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.StudentEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositories extends JpaRepository<StudentEntities,Long> {
}
