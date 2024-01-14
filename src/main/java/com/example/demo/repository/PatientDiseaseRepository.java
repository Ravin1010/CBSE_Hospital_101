package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.PatientDisease;

@Repository
public interface PatientDiseaseRepository extends JpaRepository<PatientDisease, Integer> {
    List<PatientDisease> findByUserId(Integer userId);

    List<PatientDisease> findByStatus(String status);

    List<PatientDisease> findByStatusAndRequestStatus(String status, String requestStatus);;
}
