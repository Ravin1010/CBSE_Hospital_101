package com.springRest.enitity;


//Committed in git.

import java.sql.Timestamp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "disease")
public class Disease
{
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="disease_name")
    private String diseaseName;
    @Column(name="disease_description")
    private String diseaseDescription;
    @Column(name = "created_at")
    @CreationTimestamp //this adds the default timestamp on save
    private Timestamp created_at;
    @Column(name="tp_id")
    private Integer tp_id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "tpID")
    private TreatmentPlan tp;
    
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "disease",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    public List<Doctor> doctors;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {CascadeType.DETACH, CascadeType.MERGE,
//                    CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "patient_disease",
//            joinColumns = @JoinColumn(name = "disease_id"),
//            inverseJoinColumns = @JoinColumn(name = "patient_id"))
//    private List<Patient> patientList;

    public Disease()
    {
        // empty constructor.
    }
    public Disease(String name, String description)
    {
        this.diseaseName = name;
        this.diseaseDescription = description;
    }


    public List<Doctor> getDoctors()
    {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors)
    {
        this.doctors = doctors;
    }

    public void addDoctor(Doctor doctor)
    {
        if(doctors == null)
        {
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }

    // conveniece method for medicines


//    public void addPatient(Patient patient)
//    {
//        if(patientList == null)
//        {
//            patientList = new ArrayList<>();
//        }
//        patientList.add(patient);
//    }
    public Disease(Integer id, String name, String description)
    {
        this.id = id;
        this.diseaseName = name;
        this.diseaseDescription = description;
    }

    // testiing to commit in git

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public Integer getTp_id() {
        return tp_id;
    }

    public void setTp_id(Integer tp_id) {
        this.tp_id = tp_id;
    }

//    public List<Patient> getPatientList() {
//        return patientList;
//    }
//
//    public void setPatientList(List<Patient> patientList) {
//        this.patientList = patientList;
//    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.diseaseName);
        hash = 79 * hash + Objects.hashCode(this.diseaseDescription);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Disease other = (Disease) obj;
        if (this.diseaseDescription != other.diseaseDescription) {
            return false;
        }
        if (!Objects.equals(this.diseaseName, other.diseaseName)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Id = " + id +
                ", diseaseName = '" + diseaseName + '\'' +
                ", discription = '" + diseaseDescription;
    }

    //    @Override
//    public String toString() {
//
//        var builder = new StringBuilder();
//        builder.append("City{id=").append(id).append(", name=")
//                .append(name).append(", population=")
//                .append(population).append("}");
//
//        return builder.toString();
//    }
}
