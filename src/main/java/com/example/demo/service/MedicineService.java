package com.example.demo.service;

import com.example.demo.entity.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService
{
    private MedicineRepository medicineRepository;

    public MedicineService()
    {
    }

    @Autowired
    public MedicineService(MedicineRepository medicineRepository)
    {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> getAllMedicines()
    {
        List<Medicine> medicineList = medicineRepository.findAll();
        return medicineList;
    }

    public void save(Medicine medicine)
    {
        medicineRepository.save(medicine);
    }

    public Medicine findById(int id)
    {
        Medicine newMedicine =null;
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if(medicine.isPresent())
        {
            newMedicine = medicine.get();
        }
        return newMedicine;
    }


    public void deleteById(int id)
    {
        medicineRepository.deleteById(id);
    }

    public boolean collectMedicine(int id, int collectAmount)
    {
        Medicine newMedicine =null;
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if(medicine.isPresent())
        {
            newMedicine = medicine.get();
        }
        int medicineAmount = newMedicine.getMedicineAmount();
        
        if (medicineAmount - collectAmount <0){
            return false;
        }else{
            newMedicine.setMedicineAmount(medicineAmount- collectAmount);
            medicineRepository.save(newMedicine);
            return true;
        }
    }


    public float getMedicineCostById(int medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(
                () -> new RuntimeException("Medicine not found")
        );
        return medicine.getMedicineCost();
    }

    public String getMedicineNameById(int medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(
                () -> new RuntimeException("Medicine not found")
        );
        return medicine.getMedicineName();
    }


}
