package com.example.demo.entity;

import org.springframework.context.annotation.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "doctor_name")
    private String name;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "cnic")
    private String cnic;
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "specialization")
    private String specialization;
    // @ManyToOne
    // @JoinColumn(name = "role_id")
    // private Role role;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public Doctor() {

    }

    public Doctor(String name, String fatherName, String gender, String CNIC, String email, Date dateOfBirth,
            String specialization) {
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.cnic = CNIC;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", FatherName='" + fatherName + '\'' +
                ", Gender='" + gender + '\'' +
                ", CNIC='" + cnic + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Collection<Appointment> getAppointments() {
        return null;
    }
}