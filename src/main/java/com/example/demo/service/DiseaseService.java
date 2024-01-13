package com.example.demo.service;

import com.springRest.DAO.DiseaseRepository;
import com.springRest.DAO.PatientRepository;
import com.springRest.DAO.TreatmentPlanRepository;
import com.example.demo.Throwable.DiseaseNotFoundException;
import com.example.demo.entity.Disease;
import com.example.demo.entity.Patient;
import com.example.demo.entity.TreatmentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService
{
    private DiseaseRepository diseaseRepository;
    private TreatmentPlanRepository tpRepository;

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository)
    {
        this.diseaseRepository = diseaseRepository;
    }

    public List<Disease> getAllDiseases()
    {
        List<Disease> diseaseList = diseaseRepository.findAll();
        return diseaseList;
    }
    
    public List<TreatmentPlan> listAllTreatmentPlans(){
        return (List<TreatmentPlan>) tpRepository.findAll();
    }

    public void save(Disease disease)
    {
        diseaseRepository.save(disease);
    }

    public Disease findById(int id) throws DiseaseNotFoundException
    {
        Disease newDisease =null;
        Optional<Disease> disease = diseaseRepository.findById(id);
        if(disease.isPresent())
        {
            newDisease = disease.get();
            return newDisease;
        }
        throw new DiseaseNotFoundException("Could not find any diseases with ID "+ id);
    }


    public void deleteById(int id) throws DiseaseNotFoundException
    {
        Long count = diseaseRepository.countById(id);
        if(count == null || count == 0){
            throw new DiseaseNotFoundException("Could not find any diseases with ID "+ id);
        }
        diseaseRepository.deleteById(id);
    }

}
