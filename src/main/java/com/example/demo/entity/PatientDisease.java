package com.example.demo.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_disease")
public class PatientDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "disease_id")
    private Integer diseaseID;
    @Column(name = "doctor_id")
    private Integer doctorID;
    @Column(name = "status")
    private String status;
    @Column(name = "request_status")
    private String requestStatus;
    @Column(name = "created_at")
    @CreationTimestamp // this adds the default timestamp on save
    private Timestamp created_at;

    private String docName;
    private String patientName;
    private String diseaseName;
    public PatientDisease() {
    }

    public PatientDisease(Integer id, Integer userId, Integer diseaseID, Integer doctorID, String status,
            String requestStatus, Timestamp created_at) {
        this.id = id;
        this.userId = userId;
        this.diseaseID = diseaseID;
        this.doctorID = doctorID;
        this.status = status;
        this.requestStatus = requestStatus;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientID() {
        return userId;
    }

    public void setPatientID(Integer userId) {
        this.userId = userId;
    }

    public Integer getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(Integer diseaseID) {
        this.diseaseID = diseaseID;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

}
