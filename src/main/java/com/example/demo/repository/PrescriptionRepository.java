package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    List<Prescription> findByPrescriptionStatus(String prescriptionStatus);
    List<Prescription> findByPatientId(int patientId);
    List<Prescription> findByPatientIdAndPrescriptionStatus(int patientId, String prescriptionStatus);
}
