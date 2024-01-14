package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int prescriptionId;
    private float medicineCost;
    private Integer medicineQuantity;
    private Double totalCost;
    private final Double consultationFee = 50.0;
    private String status = "NOT PAID";

    private String prescriptionName;
    private int patientId;
    private String medicineName;
    private String doctorName;

    // Constructors
    public Payment() {}

    public Payment(int prescriptionId, float medicineCost, Integer medicineQuantity, String prescriptionName,int patientId,String medicineName, String doctorName) {
        this.prescriptionId = prescriptionId;
        this.medicineCost = medicineCost;
        this.medicineQuantity = medicineQuantity;
        this.totalCost = (this.medicineCost * this.medicineQuantity) + this.consultationFee;
        this.patientId =patientId;
        this.prescriptionName = prescriptionName;
        this.medicineName = medicineName;
        this.doctorName = doctorName;

    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public float getMedicineCost() {
        return medicineCost;
    }

    public Integer getMedicineQuantity() {
        return medicineQuantity;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public String getStatus() {
        return status;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setMedicineCost(float medicineCost) {
        this.medicineCost = medicineCost;
    }

    public void setMedicineQuantity(Integer medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}

