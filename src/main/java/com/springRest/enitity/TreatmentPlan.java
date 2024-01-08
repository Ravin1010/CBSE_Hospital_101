/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.enitity;


import com.springRest.DAO.IntegerListAttributeConverter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author RyderGan
 */
@Entity
@Table(name = "treatment_plans")
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tpID;

    @Column(nullable = false, length = 100, name = "tp_name")
    private String tp_name;

    @Convert(converter = IntegerListAttributeConverter.class)
    @Column(name = "medicine_ids")
    private List<Integer> medicine_ids;
    
    @OneToMany(mappedBy = "tp")
    List<Medicine> medicines;

    @Convert(converter = IntegerListAttributeConverter.class)
    @Column(name = "medicines_amount")
    private List<Integer> medicines_amount;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @Column(nullable = false, length = 10, name = "treatment_frequency")
    private String treatment_frequency;

    @Column(name = "created_at")
    @CreationTimestamp //this adds the default timestamp on save
    private Timestamp created_at;
    
    @OneToOne(mappedBy = "tp")
    private Disease disease;

    public Integer getTpID() {
        return tpID;
    }

    public String getTp_name() {
        return tp_name;
    }

    public List<Integer> getMedicine_ids() {
        return medicine_ids;
    }

    public List<Integer> getMedicines_amount() {
        return medicines_amount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getTreatment_frequency() {
        return treatment_frequency;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public Disease getDisease() {
        return disease;
    }
    
    public void setTp_name(String tp_name) {
        this.tp_name = tp_name;
    }
    
    public void setMedicine_ids(List<Integer> medicine_ids) {
        this.medicine_ids = medicine_ids;
    }

    public void setMedicines_amount(List<Integer> medicines_amount) {
        this.medicines_amount = medicines_amount;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setTreatment_frequency(String treatment_frequency) {
        this.treatment_frequency = treatment_frequency;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    } 

}
