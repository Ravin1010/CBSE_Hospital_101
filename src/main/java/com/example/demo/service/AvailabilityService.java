package com.example.demo.service;

import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.AvailabilityRepository;
import com.example.demo.repository.DoctorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Availability;
import com.example.demo.entity.Doctor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class AvailabilityService {
    private AvailabilityRepository availabilityRepository;
    private final ObjectMapper objectMapper;
    private final DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, DoctorRepository doctorRepository,
            ObjectMapper objectMapper) {
        this.availabilityRepository = availabilityRepository;
        this.objectMapper = objectMapper;
        this.doctorRepository = doctorRepository;
    }

    public Availability getAvailabilityById(Long id) {
        return availabilityRepository.findById(id).orElse(null);
    }

    public List<Availability> getAvailabilitysByDoctorId(int doctorId) {
        List<Availability> availability = availabilityRepository.findByDoctorId(doctorId);
        return availability;
    }

    public ResponseEntity<?> getAvailableTimeSlots(int doctorId, LocalDate date)
            throws JsonProcessingException, ParseException {
        Date startDate = Date.from(date.atStartOfDay(ZoneId.of("Asia/Kuala_Lumpur")).toInstant());
        Date endDate = Date.from(date.atStartOfDay(ZoneId.of("Asia/Kuala_Lumpur")).plusDays(1).toInstant());

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        List<Availability> bookedAppointments = availabilityRepository.findByDoctorIdAndStartTimeBetween(
                doctorId, startDate, endDate);

        // Fetch shop's working hours
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        // Calculate available time slots
        List<String> availableTimeSlots = calculateAvailableTimeSlots(startDate, startTime, endTime,
                bookedAppointments);

        return ResponseEntity.ok(availableTimeSlots);
    }

    private List<String> calculateAvailableTimeSlots(Date startDate, LocalTime startTime, LocalTime endTime,
            List<Availability> bookedAppointments) {
        List<String> availableTimeSlots = new ArrayList<>();

        // Define time slot duration (59 mins)
        int slotDurationMinutes = 59;

        // Create a calendar instance to iterate over time slots
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
        calendar.setTime(startDate);

        // Initialize calendar time to the start time
        calendar.set(Calendar.HOUR_OF_DAY, startTime.getHour());
        calendar.set(Calendar.MINUTE, startTime.getMinute());

        // End time as calendar
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeZone(TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
        endCalendar.setTime(startDate);
        endCalendar.set(Calendar.HOUR_OF_DAY, endTime.getHour());
        endCalendar.set(Calendar.MINUTE, endTime.getMinute());

        while (calendar.before(endCalendar)) {
            // Check if the current time slot is within the shop's working hours
            LocalTime slotLocalTime = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE));

            if (slotLocalTime.isAfter(startTime.minusMinutes(1)) &&
                    slotLocalTime.isBefore(endTime)) {

                // Check if the current time slot is within the break time
                LocalTime breakStartTime = LocalTime.of(13, 0); // 1:00 PM
                LocalTime breakEndTime = LocalTime.of(14, 0); // 1:59 PM

                if (!(slotLocalTime.equals(breakStartTime)
                        || (slotLocalTime.isAfter(breakStartTime) && slotLocalTime.isBefore(breakEndTime)))) {
                    // Check if the current time slot is available (not in booked appointments)
                    Date slotStartTime = calendar.getTime();
                    calendar.add(Calendar.MINUTE, slotDurationMinutes);
                    Date slotEndTime = calendar.getTime();

                    boolean isSlotAvailable = true;
                    for (Availability available : bookedAppointments) {
                        if (slotStartTime.equals(available.getStartTime())) {
                            // There is an overlap with a booked appointment
                            isSlotAvailable = false;
                            break;
                        }
                    }

                    if (isSlotAvailable) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kuala_Lumpur")); // Set the timezone to
                                                                                           // Malaysia
                        String startTimeStr = dateFormat.format(slotStartTime);
                        String finishTimeStr = dateFormat.format(slotEndTime);
                        availableTimeSlots.add("startTime: " + startTimeStr + ", finishTime: " + finishTimeStr);
                    }
                }
            }
            calendar.add(Calendar.MINUTE, 1); // Move to the next time slot
        }

        return availableTimeSlots;
    }
}
