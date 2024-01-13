package com.example.demo.entity;

import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name="doctor_name")
    private String name;
    @Column(name="father_name")
    private String fatherName;
    @Column(name="gender")
    private String gender;
    @Column(name="cnic")
    private String cnic;
    @Column(name="email")
    private String email;
    @Column(name="date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    public Doctor()
    {

    }

    public Doctor(String name, String fatherName, String gender, String cnic, String email, Date dateOfBirth)
    {
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.cnic = cnic;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
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
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
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

    @Override
    public String toString()
    {
        return "Doctor{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", FatherName='" + fatherName + '\'' +
                ", Gender='" + gender + '\'' +
                ", CNIC='" + cnic + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
