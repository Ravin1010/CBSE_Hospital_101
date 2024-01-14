package com.example.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findById(int id);

    // List<Appointment> findByUserUserId(int userId);
    List<Appointment> findAllByOrderByDateAsc();

    List<Appointment> findByDoctorIsNullAndStatus(String status);

    // Optional<Appointment> findByStatus(int status);
    List<Appointment> findByUserId(int userId);
}
