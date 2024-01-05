package com.springRest.enitity;

//Committed in git.
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "medicine_name")
    private String name;
    @Column(name = "medicine_details")
    private String details;
    @Column(name = "medicine_cost")
    private Integer cost;
    @Column(name = "medicine_stock")
    private Integer stock;
    @Column(name = "type")
    private String type;

    @ManyToOne(cascade
            = {CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.PERSIST, CascadeType.REFRESH})
    private TreatmentPlan tp;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "patient_medicine",
            joinColumns = @JoinColumn(name = "medicine_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patientList;

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    // convenience method
    public void addPatient(Patient patient) {
        if (patientList == null) {
            patientList = new ArrayList<>();
        }
        patientList.add(patient);
    }

    public Medicine() {
        // no-arg constructor.
    }

    public Medicine(String name, String details, Integer cost, Integer stock, String type) {
        this.name = name;
        this.details = details;
        this.cost = cost;
        this.stock = stock;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

//    @Override
//    public String toString()
//    {
//        return "Medicine{" +
//                "id=" + id +
//                ", name='" + medicineName + '\'' +
//                ", companyName='" + companyName + '\'' +
//                ", manufactureDate=" + manufactureDate +
//                ", expiryDate=" + expiryDate +
//                '}';
//    }
}
