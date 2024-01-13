package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.PatientDisease;
import com.example.demo.entity.Prescription;
import com.example.demo.entity.User;
import com.example.demo.service.MedicineService;
import com.example.demo.service.PrescriptionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient-prescription")
public class PatientPrescriptionController
{

    // load employee data
    private PrescriptionService prescriptionService;
    private MedicineService medicineService;
    private List<Prescription> thePrescriptions;
    @Autowired

    public PatientPrescriptionController(PrescriptionService prescriptionService, MedicineService medicineService)
    {
        this.prescriptionService = prescriptionService;
        this.medicineService = medicineService;
    }

    @GetMapping("/list")
    public String listPrescriptions(Model theModel)
    {
        thePrescriptions = prescriptionService.getAllPrescriptions();
        theModel.addAttribute("prescriptions", thePrescriptions);
        return "patient-prescription/manage_patient_prescription";
    }

    // @GetMapping("/collect")
    // public String collectPrescription(@RequestParam("prescriptionId") int theID, Model model, HttpSession session)
    // {
    //      // Retrieve user details from the session
    //     User user = (User) session.getAttribute("user");

    //     // Pass user information to the model
    //     model.addAttribute("user", user);

    //     //Check if medicine amount is sufficient
    //     Prescription prescription = prescriptionService.findById(theID);
    //     boolean isMedicineSufficient = medicineService.collectMedicine(prescription.getMedicineId(), prescription.getMedicineQuantity());

    //     if (isMedicineSufficient){
    //         prescriptionService.collectById(theID);
    //     }
    //     else{
    //         //Sorry Medicine Not Enough Pop-Up
    //     }

    //     return "redirect:manage-patient-prescription";
    // }

    @PostMapping("/collect")
    @ResponseBody
    public String collectPrescription(@RequestParam("prescriptionId") int theID) {
        Prescription prescription = prescriptionService.findById(theID);
        if (medicineService.collectMedicine(prescription.getMedicineId(), prescription.getMedicineQuantity())) {

            // Update the prescription status
            prescriptionService.collectById(theID);
            return "Medicine Dispense Successfull";
        } else {
            return "We are sorry, the Medicine Stock in Pharmacy is insufficient. Please try again later";
        }
    }

    @GetMapping("/manage-patient-prescription")
    public String managePrescription(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        model.addAttribute("acceptedPrescriptionList", prescriptionService.getNewPrescriptionsById(user.getUser_id()));
        model.addAttribute("collectedPrescriptionList", prescriptionService.getOldPrescriptionsById(user.getUser_id()));
        // Other logic for the manage patient record page
        return "/patient_prescription/manage_patient_prescription";
    }

}










