/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.service;

import com.springRest.DAO.DiseaseRepository;
import com.springRest.DAO.DoctorRepository;
import com.springRest.DAO.PatientDiseaseRepository;
import com.springRest.DAO.PatientRepository;
import com.springRest.Throwable.PatientDiseaseNotFoundException;
import com.springRest.enitity.Disease;
import com.springRest.enitity.Doctor;
import com.springRest.enitity.Patient;
import com.springRest.enitity.PatientDisease;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RyderGan
 */
@Service
public class PatientDiseaseService {
    private PatientDiseaseRepository repo;
    private PatientRepository patientRepo;
    private DoctorRepository doctorRepo;
    private DiseaseRepository diseaseRepo;
    
    @Autowired
    public PatientDiseaseService(PatientDiseaseRepository pdRepository, PatientRepository patientRepo, DoctorRepository doctorRepository, DiseaseRepository diseaseRepository)
    {
        this.repo = pdRepository;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepository;
        this.diseaseRepo = diseaseRepository;
    }
    
    public List<PatientDisease> getAllPatientDiseases()
    {
        List<PatientDisease> pdList = repo.findAll();
        return pdList;
    }
    
    public List<PatientDisease> listAllPDs(List<PatientDisease> allPDs) {
        //Initialize big array
        //List<List<String>> allResultMedicines = new ArrayList<>();
        for (int i = 0; i < allPDs.size(); i++) {
            //Get all patient ID for each result
            Integer patientID = allPDs.get(i).getPatientID();
            Integer doctorID = allPDs.get(i).getDoctorID();
            Integer diseaseID = allPDs.get(i).getDiseaseID();
            Patient patientInfo = patientRepo.getOne(patientID);
            Doctor doctorInfo = doctorRepo.getOne(doctorID);
            Disease diseaseInfo = diseaseRepo.getOne(diseaseID);
            allPDs.get(i).setPatientName(patientInfo.getName());
            allPDs.get(i).setDocName(doctorInfo.getName());
            allPDs.get(i).setDiseaseName(diseaseInfo.getDiseaseName());
        }
        return allPDs;
    }

    public void save(PatientDisease pd)
    {
        repo.save(pd);
    }
    
    public void update(PatientDisease pd) throws PatientDiseaseNotFoundException  {
        PatientDisease updatePD = repo.findById(pd.getId()).orElse(null);
        System.out.println(updatePD.getId());
        if (updatePD != null) {
            updatePD.setDiseaseID(pd.getDiseaseID());
            updatePD.setDoctorID(pd.getDoctorID());
            updatePD.setPatientID(pd.getPatientID());
            updatePD.setStatus(pd.getStatus());
            updatePD.setRequestStatus(pd.getRequestStatus());
            repo.save(updatePD);
        }
        throw new PatientDiseaseNotFoundException("Could not find any PD with ID "+ pd.getId().toString());
    }

    public PatientDisease findById(int id) throws PatientDiseaseNotFoundException
    {
        PatientDisease newPD =null;
        Optional<PatientDisease> pd = repo.findById(id);
        if(pd.isPresent())
        {
            newPD = pd.get();
            return newPD;
        }
        throw new PatientDiseaseNotFoundException("Could not find any PD with ID "+ id);
    }


    public void deleteById(int id) throws PatientDiseaseNotFoundException
    {
        Long count = repo.countById(id);
        if(count == null || count == 0){
            throw new PatientDiseaseNotFoundException("Could not find any diseases with ID "+ id);
        }
        repo.deleteById(id);
    }
}
