package com.example.demo.controller;

import com.example.demo.entity.Availability;
import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.demo.service.AvailabilityService;
import com.example.demo.service.DiseaseService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/avalability")
public class AvailabilityController {

    // load employee data
    private AvailabilityService availabilityService;
    private DoctorService doctorService;
    private List<Availability> available;

    public AvailabilityController(AvailabilityService availabilityService, DoctorService doctorService) {
        this.availabilityService = availabilityService;
        this.doctorService = doctorService;
    }

    @GetMapping("/available-slots")
    public ResponseEntity<?> getAvailableTimeSlotsByShopIdAndDate(
            @PathVariable int doctorId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
            throws JsonProcessingException, ParseException {

        return availabilityService.getAvailableTimeSlots(doctorId, date);
    }
}
