/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TreatmentPlan;

/**
 *
 * @author RyderGan
 */
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Integer> {
    public Long countBytpID(Integer id);
}
