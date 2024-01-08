package com.example.demo.entity;

//Committed in git.

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="doctor_name")
    private String Name;
    @Column(name="father_name")
    private String fatherName;
    @Column(name="gender")
    private String Gender;
    @Column(name="cnic")
    private String cnic;
    @Column(name="email")
    private String email;
    @Column(name="date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @Column(name="availability")
    private String availability;

    @ManyToOne(cascade =
            {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "doctor",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    public List<Patient> patients;

    public Doctor()
    {

    }

    public Doctor(String name, String fatherName, String gender, String CNIC, String email, Date dateOfBirth, String availability)
    {
        Name = name;
        this.fatherName = fatherName;
        Gender = gender;
        this.cnic = CNIC;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.availability = availability;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public void setFatherName(String fatherName)
    {
        this.fatherName = fatherName;
    }

    public String getGender()
    {
        return Gender;
    }

    public void setGender(String gender)
    {
        Gender = gender;
    }

    public String getCnic()
    {
        return cnic;
    }

    public void setCnic(String cnic)
    {
        this.cnic = cnic;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvailability()
    {
        return availability;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    public List<Patient> getPatients()
    {
        return patients;
    }

    public void setPatients(List<Patient> patients)
    {
        this.patients = patients;
    }

    public Disease getDisease()
    {
        return disease;
    }

    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }

    public void addPatient(Patient patient)
    {
        if(patients==null)
        {
            patients = new ArrayList<>();
        }
        patients.add(patient);
        patient.setDoctor(this);
    }
    @Override
    public String toString()
    {
        return "Doctor{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", FatherName='" + fatherName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", CNIC='" + cnic + '\'' +
                ", email='" + email + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
