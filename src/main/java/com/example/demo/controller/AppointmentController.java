package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.User;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.DoctorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointment-history")
    public String manageAppointment(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Retrieve appointment data based on user ID
        List<Appointment> appointments = appointmentRepository.findByUserId(user.getUser_id());

        model.addAttribute("appointments", appointments);

        // Pass user information to the model
        model.addAttribute("user", user);

        return "appointment-history";
    }

    // //Get appointment history of a patient based on ID
    // @GetMapping("/appointment-history")
    // public String showAppointments(Model model, HttpSession session) {
    // User user = (User) session.getAttribute("user");

    // int userId = user.getUser_id();
    // List<Appointment> appointmentHistory =
    // appointmentService.getAppointmentHistoryByUserId(userId);
    // model.addAttribute("appointmentHistory", appointmentHistory);
    // model.addAttribute("user", user);

    // return "appointment-history";
    // }

    // //Get appointment history of all patient
    // @GetMapping("/appointment-history")
    // public String showAppointments(Model model) {
    // List<Appointment> appointments = appointmentService.getAllAppointments();
    // model.addAttribute("appointments", appointments);

    // return "appointment-history";
    // }

    @GetMapping("/book-appointment")
    public String showBookAppointmentForm(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Add logic to populate necessary data for the form (e.g., doctors, available
        // slots)
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        // Create an empty Appointment object for the form submission
        model.addAttribute("newAppointment", new Appointment());

        // Pass user information to the model
        model.addAttribute("user", user);

        return "book-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@ModelAttribute("newAppointment") Appointment newAppointment, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");
        // Set the userId for the newAppointment
        newAppointment.setUserId(user.getUser_id());
        // Add logic to save the new appointment to the database
        appointmentService.bookAppointment(newAppointment);

        // Redirect to the appointments page or show a success message
        return "redirect:/appointment-history";
    }
    // @PostMapping("/bookAppointment")
    // @CrossOrigin
    // public ResponseEntity<Appointment> bookAppointment(@RequestParam String key,
    // @RequestBody Appointment appointment, HttpSession session) throws
    // LoginException, IOException{
    // // if(appointment == null) {
    // // throw new AppointmentException("Please enter valid appointment");
    // // }

    // if(loginService.checkUserLoginOrNot(key)) {
    // User user = (User) session.getAttribute("user");

    // Appointment bookAppointment = patientService.bookAppointment(key,
    // appointment);

    // return new ResponseEntity<Appointment>(bookAppointment, HttpStatus.CREATED);

    // }else {

    // throw new LoginException("Invalid key or please login first");

    // }
    // }

    @GetMapping("/pending-appointments")
    public String showPendingAppointments(Model model) {
        List<Appointment> pendingAppointments = appointmentService.getUnassignedAppointments("Pending");
        model.addAttribute("pendingAppointments", pendingAppointments);
        return "pending-appointments";
    }

    // @GetMapping("/assign-doctor-form")
    // public String showAssignDoctorForm(@RequestParam int appointmentId, Model
    // model) {
    // // List<Appointment> unassignedAppointments =
    // appointmentService.getUnassignedAppointments();
    // // List<Doctor> availableDoctors = doctorRepository.findAll();
    // // List<Doctor> availableDoctors =
    // appointmentService.getAvailableDoctorsAtSlot(appointmentId);
    // // model.addAttribute("unassignedAppointments", unassignedAppointments);
    // List<Doctor> availableDoctors =
    // appointmentService.getAvailableDoctorsAtSlot(appointmentId);
    // model.addAttribute("appointmentId", appointmentId);
    // model.addAttribute("availableDoctors", availableDoctors);

    // return "assign-doctor-admin";
    // }

    @PostMapping("/assign-doctor")
    public String assignDoctorToAppointment(@RequestParam int appointmentId, @RequestParam int doctorId) {
        appointmentService.assignDoctorToAppointment(appointmentId, doctorId);
        return "redirect:pending-appointments";
    }

    @GetMapping("/get-status")
    @ResponseBody
    public String getAppointmentStatus(@RequestParam int appointmentId) {
        return appointmentService.getAppointmentStatus(appointmentId);
    }
}

// @GetMapping("/addDoctor")
// public String getDoctorForm(Model model)
// {
// Doctor Doctor = new Doctor();
// model.addAttribute("diseaseList",diseaseService.getAllDiseases());
// model.addAttribute("doctor",Doctor);
// return "doctors/addDoctor";
// }

// @PostMapping("/save")
// public String saveDoctor(@ModelAttribute("doctor") Doctor theDoctor)
// {

// doctorService.save(theDoctor);
// return "redirect:/doctors/list";
// }

// @PutMapping("/updateAppointment")
// @CrossOrigin
// public ResponseEntity<Appointment> updateAppointment(@RequestParam String
// key, @RequestBody Appointment newAppointment) throws LoginException,
// AppointmentException, PatientException, DoctorException, IOException,
// TimeDateException{

// if(loginService.checkUserLoginOrNot(key)) {

// Appointment updatedAppointment = patientService.updateAppointment(key,
// newAppointment);

// return new ResponseEntity<Appointment>(updatedAppointment,
// HttpStatus.ACCEPTED);

// }else {

// throw new LoginException("Invalid key or please login first");

// }

// }

// @PostMapping("/availableTiming")
// @CrossOrigin
// public ResponseEntity<List<LocalDateTime>>
// getAvailbleTimingOfDoctor(@RequestParam String key, @RequestBody Doctor
// doctor) throws IOException, TimeDateException, LoginException,
// DoctorException{

// if(loginService.checkUserLoginOrNot(key)) {

// List<LocalDateTime> listOfAvailable =
// doctorService.getDoctorAvailableTimingForBooking(key, doctor);

// return new ResponseEntity<List<LocalDateTime>>(listOfAvailable,
// HttpStatus.ACCEPTED);

// }else {

// throw new LoginException("Invalid key or please login first");

// }
// }

// @GetMapping("/getAllDoctors")
// @CrossOrigin
// public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam String key)
// throws LoginException, DoctorException{
// if(loginService.checkUserLoginOrNot(key)) {

// List<Doctor> listOfDoctors = patientService.getAllDoctors();

// return new ResponseEntity<List<Doctor>>(listOfDoctors, HttpStatus.ACCEPTED);

// }else {

// throw new LoginException("Invalid key or please login first");

// }
// }

// @DeleteMapping("/appointment")
// @CrossOrigin
// public ResponseEntity<Appointment> deleteAppointment(@RequestParam String
// key, @RequestBody Appointment appointment) throws AppointmentException,
// DoctorException, Exception{

// if(loginService.checkUserLoginOrNot(key)) {

// Appointment listOfDoctors = patientService.deleteAppointment(appointment);

// return new ResponseEntity<Appointment>(listOfDoctors, HttpStatus.ACCEPTED);

// }else {

// throw new LoginException("Invalid key or please login first");

// }
// }
