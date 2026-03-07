package com.example.springjpaproject1.repositories;

import com.example.springjpaproject1.entities.AdmissionRecord;
import com.example.springjpaproject1.entities.StudentEntities;
import org.hibernate.id.uuid.LocalObjectUuidHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepositories extends JpaRepository<AdmissionRecord, Long> {

    @Query(value = "SELECT a.id,s.name from student s join admission_record a where s.id=a.student_entities_id and a.id=?1",nativeQuery = true)
    List<StudentEntities> getStudentsByTheirMainId();
}
