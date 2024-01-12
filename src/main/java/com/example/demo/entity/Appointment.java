package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentId;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;
    @Column(name = "user_id")
    private int userId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "slot")
    private LocalTime slot;

    @Column(name = "treatment_type")
    private String treatmentType;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "note")
    private String note;

    public Appointment() {
    }

    public Appointment(int appointmentId, int userId, LocalDate date, LocalTime slot, String treatmentType, String status,
        int doctorId, String note) {
        this.appointmentId = appointmentId;
        // this.userId = userId;
        this.date = date;
        this.slot = slot;
        this.treatmentType = treatmentType;
        this.status = status;
        // this.doctorId = doctorId;
        this.note = note;
    }

    // public int getAppointmentId() {
    //     return this.appointmentId;
    // }

    // public void setAppointmentId(int appointmentId) {
    //     this.appointmentId = appointmentId;
    // }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getSlot() {
        return this.slot;
    }

    public void setSlot(LocalTime slot) {
        this.slot = slot;
    }

    public String getTreatmentType() {
        return this.treatmentType;
    }
    
    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // public int getDoctorId() {
    //     return this.doctorId;
    // }
    public Doctor getDoctor()
    {
        return doctor;
    }

    public void setDoctor(Doctor doctor)
    {
        this.doctor = doctor;
    }
    // public void setDoctorId(int doctorId) {
    //     this.doctorId = doctorId;
    // }


    public String getNote() {
        return this.treatmentType;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    // public Collection<Appointment> getAppointments() {
    //     return null;
    // }
            
}
