package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_disease")
public class PatientDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pd_id")
    private int pdId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "status")
    private String status;

    @Column(name = "request_status")
    private String requestStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public PatientDisease() {
    }

    public PatientDisease(int pdId, int userId, String doctorName, String diseaseName, String status,
            String requestStatus, LocalDateTime createdAt) {
        this.pdId = pdId;
        this.userId = userId;
        this.doctorName = doctorName;
        this.diseaseName = diseaseName;
        this.status = status;
        this.requestStatus = requestStatus;
        this.createdAt = createdAt;
    }

    public int getPdId() {
        return this.pdId;
    }

    public void setPdId(int pdId) {
        this.pdId = pdId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDiseaseName() {
        return this.diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestStatus() {
        return this.requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
