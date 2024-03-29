/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Throwable.TreatmentPlanNotFoundException;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.TreatmentPlan;
import com.example.demo.repository.TreatmentPlanRepository;

/**
 *
 * @author RyderGan
 */
@Service
public class TreatmentPlanService {

    private TreatmentPlanRepository repo;
    private MedicineService medService;

    @Autowired
    public TreatmentPlanService(TreatmentPlanRepository repo, MedicineService medService) {
        this.repo = repo;
        this.medService = medService;
    }

    public List<TreatmentPlan> listAll() {
        return (List<TreatmentPlan>) repo.findAll();
    }

    public List<TreatmentPlan> listAllMedicines(List<TreatmentPlan> listTreatmentPlans) {
        // Initialize big array
        // List<List<String>> allResultMedicines = new ArrayList<>();
        for (int i = 0; i < listTreatmentPlans.size(); i++) {
            // Get all medicine_ids for each result
            List<Integer> medicine_ids = listTreatmentPlans.get(i).getMedicine_ids();

            // Add a null check for medicine_ids
            if (medicine_ids != null) {
                List<Medicine> medicines = new ArrayList<>();
                for (int j = 0; j < medicine_ids.size(); j++) {
                    Medicine medicine = medService.findById(medicine_ids.get(j));
                    medicine.setStock(listTreatmentPlans.get(i).getMedicines_amount().get(j));
                    medicines.add(medicine);
                }
                listTreatmentPlans.get(i).setMedicines(medicines);
            }
        }
        return listTreatmentPlans;
    }

    public void save(TreatmentPlan tp) {
        repo.save(tp);
    }

    public TreatmentPlan findById(Integer id) throws TreatmentPlanNotFoundException {
        Optional<TreatmentPlan> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new TreatmentPlanNotFoundException("Could not find any TreatmentPlan with ID " + id);
    }

    public void update(TreatmentPlan tp) throws TreatmentPlanNotFoundException {
        TreatmentPlan updateTp = repo.findById(tp.getTpID()).orElse(null);
        updateTp.setStart_date(tp.getStart_date());
        updateTp.setEnd_date(tp.getEnd_date());
        updateTp.setTp_name(tp.getTp_name());
        updateTp.setTreatment_frequency(tp.getTreatment_frequency());
        updateTp.setMedicine_ids(tp.getMedicine_ids());
        updateTp.setMedicines_amount(tp.getMedicines_amount());
        repo.save(updateTp);
    }

    public void delete(Integer id) throws TreatmentPlanNotFoundException {
        Long count = repo.countBytpID(id);
        if (count == null || count == 0) {
            throw new TreatmentPlanNotFoundException("Could not find any diseases with ID " + id);
        }
        repo.deleteById(id);
    }
}
