package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Table(name = "availability")
@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor")
    @JsonBackReference
    private Doctor doctor;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "finish_Time")
    private Date finishTime;

    @Column(name="available")
    private String available;

    public long getId() {
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setId(Doctor doctor){
        this.doctor = doctor;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Date finishTime){
        this.finishTime = finishTime;
    }

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(String available){
        this.available = available;
    }
}
