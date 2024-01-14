package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
    public Long countById(int id);
}
