package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    List<Medicine> findByMedicineId(int medicineId);
    List<Medicine> findByMedicineName(String medicineName);
}
