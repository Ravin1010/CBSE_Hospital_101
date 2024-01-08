package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private int medicineId;

    @Column(name = "medicine_name")
    private String medicineName;

    @Column(name = "medicine_details")
    private String medicineDetails;

    @Column(name = "medicine_cost")
    private float medicineCost;

    @Column(name = "medicine_amount")
    private int medicineAmount;

    public Medicine() {
    }

    public Medicine(int medicineId, String medicineName, String medicineDetails, float medicineCost, int medicineAmount) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineDetails = medicineDetails;
        this.medicineCost = medicineCost;
        this.medicineAmount = medicineAmount;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(String medicineDetails) {
        this.medicineDetails = medicineDetails;
    }

    public float getMedicineCost() {
        return medicineCost;
    }

    public void setMedicineCost(float medicineCost) {
        this.medicineCost = medicineCost;
    }

    public int getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(int medicineAmount) {
        this.medicineAmount = medicineAmount;
    }

}
