package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "medicine_name")
    private String name;

    @Column(name = "medicine_details")
    private String details;

    @Column(name = "medicine_cost")
    private float cost;

    @Column(name = "medicine_stock")
    private int stock;

    public Medicine() {
    }

    public Medicine(int id, String name, String details, float cost, int stock) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.cost = cost;
        this.stock = stock;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    
}
