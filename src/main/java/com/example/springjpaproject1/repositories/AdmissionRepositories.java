package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.AdmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepositories extends JpaRepository<AdmissionRecord, Long> {


    @Query("SELECT DISTINCT ar FROM AdmissionRecord ar " +
            "JOIN FETCH ar.studentEntities s " +
            "LEFT JOIN FETCH s.professorEntities " +
            "LEFT JOIN FETCH s.subjectEntities")
    List<AdmissionRecord> findAllWithFullDetails();
}
