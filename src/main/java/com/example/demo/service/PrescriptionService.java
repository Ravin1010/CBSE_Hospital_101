package com.example.demo.service;

import com.example.demo.entity.Prescription;
import com.example.demo.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService
{
    private PrescriptionRepository prescriptionRepository;

    public PrescriptionService()
    {
    }

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository)
    {
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Prescription> getAllPrescriptions()
    {
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        return prescriptionList;
    }

    public List<Prescription> getNewPrescriptions()
    {
        String prescriptionStatus = "New";
        List<Prescription> newPrescriptionList = prescriptionRepository.findByPrescriptionStatus(prescriptionStatus);
        return newPrescriptionList;
    }

    public List<Prescription> getOldPrescriptions()
    {
        String prescriptionStatus = "New";
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        List<Prescription> newPrescriptionList = prescriptionRepository.findByPrescriptionStatus(prescriptionStatus);
        prescriptionList.removeAll(newPrescriptionList);
        return prescriptionList;
    }

    public List<Prescription> getNewPrescriptionsById(int patientId)
    {
        String prescriptionStatus = "Accepted";
        List<Prescription> acceptedPrescriptionList = prescriptionRepository.findByPatientIdAndPrescriptionStatus(patientId, prescriptionStatus);
        return acceptedPrescriptionList;
    }

    public List<Prescription> getOldPrescriptionsById(int patientId)
    {
        String prescriptionStatus = "Collected";
        List<Prescription> collectedPrescriptionList = prescriptionRepository.findByPatientIdAndPrescriptionStatus(patientId, prescriptionStatus);
        return collectedPrescriptionList;
    }

    public void save(Prescription prescription)
    {
        prescriptionRepository.save(prescription);
    }

    public Prescription findById(int id)
    {
        Prescription newPrescription =null;
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if(prescription.isPresent())
        {
            newPrescription = prescription.get();
        }
        return newPrescription;
    }


    public void acceptById(int id)
    {
        //Get Prescription
        Prescription newPrescription =null;
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if(prescription.isPresent())
        {
            newPrescription = prescription.get();
        }

        newPrescription.setPrescriptionStatus("Accepted");
        prescriptionRepository.save(newPrescription);
    }

    public void rejectById(int id)
    {
        //Get Prescription
        Prescription newPrescription =null;
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if(prescription.isPresent())
        {
            newPrescription = prescription.get();
        }

        newPrescription.setPrescriptionStatus("Rejected");
        prescriptionRepository.save(newPrescription);
    }

    public void collectById(int id)
    {
        //Get Prescription
        Prescription newPrescription =null;
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if(prescription.isPresent())
        {
            newPrescription = prescription.get();
        }

        newPrescription.setPrescriptionStatus("Collected");
        prescriptionRepository.save(newPrescription);
    }
}
