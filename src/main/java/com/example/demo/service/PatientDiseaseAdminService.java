/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Throwable.PatientDiseaseAdminNotFoundException;
import com.example.demo.entity.Disease;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.PatientDisease;
import com.example.demo.entity.User;
import com.example.demo.repository.DiseaseRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientDiseaseAdminRepository;
import com.example.demo.repository.UserRepository;

/**
 *
 * @author RyderGan
 */
@Service
public class PatientDiseaseAdminService {
    private PatientDiseaseAdminRepository repo;
    private UserRepository userRepository;
    private DoctorRepository doctorRepo;
    private DiseaseRepository diseaseRepo;

    @Autowired
    public PatientDiseaseAdminService(PatientDiseaseAdminRepository pdRepository, UserRepository userRepository,
            DoctorRepository doctorRepository, DiseaseRepository diseaseRepository) {
        this.repo = pdRepository;
        this.userRepository = userRepository;
        this.doctorRepo = doctorRepository;
        this.diseaseRepo = diseaseRepository;
    }

    public List<PatientDisease> getAllPatientDiseases() {
        List<PatientDisease> pdList = repo.findAll();
        return pdList;
    }

    public List<PatientDisease> listAllPDs(List<PatientDisease> allPDs) {
        // Initialize big array
        // List<List<String>> allResultMedicines = new ArrayList<>();
        for (int i = 0; i < allPDs.size(); i++) {
            // Get all patient ID for each result
            Integer patientID = allPDs.get(i).getUserId();
            Integer doctorID = allPDs.get(i).getDoctorID();
            Integer diseaseID = allPDs.get(i).getDiseaseID();
            User patientInfo = userRepository.getById(patientID);
            Doctor doctorInfo = doctorRepo.getById(doctorID);
            Disease diseaseInfo = diseaseRepo.getById(diseaseID);
        }
        return allPDs;
    }

    public void save(PatientDisease pd) {
        repo.save(pd);
    }

    public void update(PatientDisease pd) throws PatientDiseaseAdminNotFoundException {
        PatientDisease updatePD = repo.findById(pd.getId()).orElse(null);
        System.out.println(updatePD.getId());
        updatePD.setDiseaseID(pd.getDiseaseID());
        updatePD.setDoctorID(pd.getDoctorID());
        updatePD.setUserId(pd.getUserId());
        updatePD.setStatus(pd.getStatus());
        updatePD.setRequestStatus(pd.getRequestStatus());
        repo.save(updatePD);
    }

    public PatientDisease findById(int id) throws PatientDiseaseAdminNotFoundException {
        PatientDisease newPD = null;
        Optional<PatientDisease> pd = repo.findById(id);
        if (pd.isPresent()) {
            newPD = pd.get();
            return newPD;
        }
        throw new PatientDiseaseAdminNotFoundException("Could not find any PD with ID " + id);
    }

    public void deleteById(int id) throws PatientDiseaseAdminNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new PatientDiseaseAdminNotFoundException("Could not find any diseases with ID " + id);
        }
        repo.deleteById(id);
    }
}
