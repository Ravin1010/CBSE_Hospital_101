package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.PatientDisease;
import com.example.demo.entity.User;
import com.example.demo.repository.PatientDiseaseRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient-disease")
public class PatientDiseaseController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PatientDiseaseRepository patientDiseaseRepository;

    @GetMapping("/manage-patient-record-user")
    public String managePatientRecord(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Retrieve patient disease data based on user ID
        List<PatientDisease> patientDiseases = patientDiseaseRepository.findByPatientID(user.getUser_id());

        model.addAttribute("patientDiseases", patientDiseases);

        // Pass user information to the model
        model.addAttribute("user", user);

        // Other logic for the manage patient record page
        return "patient_disease/manage_patient_record_user";
    }

    @PostMapping("/update-request-status")
    @ResponseBody
    public String updateRequestStatus(@RequestParam int id) {
        // Find the patient disease record by ID
        Optional<PatientDisease> optionalPatientDisease = patientDiseaseRepository.findById(id);

        if (optionalPatientDisease.isPresent()) {
            PatientDisease patientDisease = optionalPatientDisease.get();

            // Update the request status
            patientDisease.setRequestStatus("yes");

            // Save the updated record
            patientDiseaseRepository.save(patientDisease);

            return "Success";
        } else {
            return "Error: Patient Disease record not found";
        }
    }

    @GetMapping("/manage-patient-record-admin")
    public String managePatientRecordAdmin(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Retrieve patient disease data based on user ID
        List<PatientDisease> patientDiseases = patientDiseaseRepository.findByStatusAndRequestStatus("active", "yes");

        // Pass user information to the model
        model.addAttribute("user", user);
        model.addAttribute("patientDiseases", patientDiseases);

        // Other logic for the manage patient record page
        return "patient_disease/manage_patient_record_admin";
    }

    @PostMapping("/update-patient-record-status")
    @ResponseBody
    public String updatePatientRecordStatus(@RequestParam int id) {
        // Find the patient disease record by ID
        Optional<PatientDisease> optionalPatientDisease = patientDiseaseRepository.findById(id);

        if (optionalPatientDisease.isPresent()) {
            PatientDisease patientDisease = optionalPatientDisease.get();

            // Update the request status
            patientDisease.setStatus("inactive");

            // Save the updated record
            patientDiseaseRepository.save(patientDisease);

            return "Success";
        } else {
            return "Error: Patient Disease record not found";
        }
    }
}
