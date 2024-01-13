/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.DAO;

import com.springRest.enitity.PatientDiseaseAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author RyderGan
 */
public interface PatientDiseaseAdminRepository extends JpaRepository<PatientDiseaseAdmin, Integer>
{
    public Long countById(int id);
}
