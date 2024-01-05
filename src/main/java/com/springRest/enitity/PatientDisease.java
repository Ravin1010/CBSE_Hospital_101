/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.enitity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author RyderGan
 */
@Entity
@Table(name = "patient_disease")
public class PatientDisease {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="patient_id")
    private Integer patientID;
    @Column(name="disease_id")
    private Integer diseaseID;
    @Column(name="doctor_id")
    private Integer doctorID;
    @Column(name ="status")
    private String status;
    @Column(name ="request_status")
    private String requestStatus;
    @Column(name = "created_at")
    @CreationTimestamp //this adds the default timestamp on save
    private Timestamp created_at;
    
    private String docName;
    private String patientName;
    private String diseaseName;

    public PatientDisease() {
    }
    
    public PatientDisease(Integer patientID, Integer diseaseID, Integer doctorID, String status, String requestStatus) {
        this.patientID = patientID;
        this.diseaseID = diseaseID;
        this.doctorID = doctorID;
        this.status = status;
        this.requestStatus = requestStatus;
    }

    public Integer getId() {
        return id;
    }
    
    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
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

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
    
}
