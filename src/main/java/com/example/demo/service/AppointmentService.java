package com.example.demo.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    
    // public AppointmentService(AppointmentRepository appointmentRepository)
    // {
    //     this.appointmentRepository = appointmentRepository;
    // }

    public List<Appointment> getAllAppointments()
    {
        // List<Appointment> appointmentList = appointmentRepository.findAll();
        // return appointmentList;
        return appointmentRepository.findAllByOrderByDateAsc();
    }

    public void save(Appointment appointment)
    {
        appointmentRepository.save(appointment);
    }

    public Appointment findById(int id)
    {
        Appointment newAppointment =null;
        Optional<Appointment> user = appointmentRepository.findById(id);
        if(user.isPresent())
        {
            newAppointment = user.get();
        }
        return newAppointment;
    }


    public void deleteById(int id)
    {
        Appointment appointment = this.findById(id);
        appointmentRepository.delete(appointment);
        //appointmentRepository.deleteById(id);
    }

    public List<Appointment> getUnassignedAppointments(String status) {
        return appointmentRepository.findByDoctorIsNullAndStatus(status);
    }

    public void assignDoctorToAppointment(int appointmentId, int doctorId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalAppointment.isPresent() && optionalDoctor.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            Doctor doctor = optionalDoctor.get();

            appointment.setDoctor(doctor);
            appointment.setStatus("Assigned");

            appointmentRepository.save(appointment);
        }
    }

    // public List<Doctor> getAvailableDoctorsAtSlot(int appointmentId) {
    //      // Retrieve appointment details
    //     Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
    //     if (!optionalAppointment.isPresent()) {
    //         // Handle case where appointment is not found
    //         return Collections.emptyList();
    //     }

    //     Appointment appointment = optionalAppointment.get();
    //     LocalDate appointmentDate = appointment.getDate();
    //     LocalTime appointmentSlot = appointment.getSlot();

    //     // Fetch all doctors
    //     List<Doctor> allDoctors = doctorRepository.findAll();

    //     // Implement logic to filter available doctors based on date, slot, or other criteria
    //     // Filter doctors based on availability
    //     List<Doctor> availableDoctors = allDoctors.stream()
    //             .filter(doctor -> isDoctorAvailable(doctor, appointmentDate, appointmentSlot))
    //             .collect(Collectors.toList());

    //     return availableDoctors;    }
    
    // private boolean isDoctorAvailable(Doctor doctor, LocalDate appointmentDate, LocalTime appointmentSlot) {
    //         // Implement your logic here to check if the doctor is available at the specified date and slot
    //         // You may need to check the doctor's schedule or other criteria based on your business rules
    
    //         // For simplicity, let's assume doctors are available if they have no appointments at the specified date and slot
    //         return doctor.getAppointments().stream()
    //                 .noneMatch(appointment -> isAppointmentAtSameDateAndSlot(appointment, appointmentDate, appointmentSlot));
    // }
    
    // private boolean isAppointmentAtSameDateAndSlot(Appointment appointment, LocalDate appointmentDate, LocalTime appointmentSlot) {
    //         return appointment.getDate().equals(appointmentDate)
    //                 && appointment.getSlot().equals(appointmentSlot);
    // }

    public List<Appointment> getPendingAppointments(String status) {
        // Fetch all appointments
        List<Appointment> allAppointments = appointmentRepository.findAllByOrderByDateAsc();

        // Implement logic to filter appointments based on status with a null check
        List<Appointment> pendingAppointments = allAppointments.stream()
            .filter(appointment -> status == null || status.equals(appointment.getStatus()))
            .collect(Collectors.toList());
        return pendingAppointments;
        
    }

    public String getAppointmentStatus(int appointmentId) {
        // Assuming you have a method to find an appointment by ID
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);

        // Check if the appointment exists and return its status, or a default value
        return (appointment != null) ? appointment.getStatus() : "unknown";
    }

    public void bookAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);    }

    // public List<Appointment> getAppointmentHistoryByUserId(int userId){

    //     return appointmentRepository.findByUserUserId(userId);    }
}



