package com.example.demo.repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByDoctorId(int doctorId);

    List<Availability> findByDoctorIdAndStartTimeBetweenAndFinishTimeBetween(
                    int doctorId, LocalTime startTime, LocalTime endTime, LocalTime startTime2, LocalTime endTime2);

    List<Availability> findByDoctorIdAndStartTimeBetweenAndFinishTimeBetweenAndIdNot(
                    int doctorId, LocalTime startTime1, LocalTime finishTime1, LocalTime startTime2, LocalTime finishTime2, Long id);

    List<Availability> findByDoctorIdAndStartTimeBetween(int doctorId, Date startDate, Date endDate);
}
