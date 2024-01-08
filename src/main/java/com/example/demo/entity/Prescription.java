package com.example.demo.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private int prescriptionId;

    @Column(name = "prescription_name")
    private String prescriptionName;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "tp_name")
    private String tpName;

    @Column(name = "medicineId")
    private int medicineId;

    @Column(name = "medicine_quantity")
    private int medicineQuantity;

    @Column(name = "prescription_status")
    private String prescriptionStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Prescription() {
    }

    public Prescription(int prescriptionId, String prescriptionName, int patientId, String doctorName,
            String diseaseName, String tpName, int medicineId, int medicineQuantity, String prescriptionStatus,
            LocalDateTime createdAt) {
        this.prescriptionId = prescriptionId;
        this.prescriptionName = prescriptionName;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.diseaseName = diseaseName;
        this.tpName = tpName;
        this.medicineId = medicineId;
        this.medicineQuantity = medicineQuantity;
        this.prescriptionStatus = prescriptionStatus;
        this.createdAt = createdAt;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(int medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus(String prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
