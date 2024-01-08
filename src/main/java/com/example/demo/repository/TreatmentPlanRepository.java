/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.DAO;

import com.springRest.enitity.TreatmentPlan;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author RyderGan
 */
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Integer>{
    public Long countBytpID(Integer id);
}
