package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Prescription;
import com.example.demo.entity.User;
import com.example.demo.service.PrescriptionService;
import com.example.demo.service.UserService;
import com.example.demo.service.MedicineService;
import com.example.demo.service.DiseaseService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.TreatmentPlanService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private TreatmentPlanService treatmentPlanService;

    public PrescriptionController(PrescriptionService prescriptionService, UserService userService, DoctorService doctorService,
            MedicineService medicineService, DiseaseService diseaseService, TreatmentPlanService treatmentPlanService) {
        this.prescriptionService = prescriptionService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.medicineService = medicineService;
        this.diseaseService = diseaseService;
        this.treatmentPlanService = treatmentPlanService;
    }

    @GetMapping("/add-prescription")
    public String getPrescriptionForm(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass information to the model
        model.addAttribute("user", user);
        model.addAttribute("patientList", userService.getAllPatients());
        model.addAttribute("doctorList", doctorService.getAllDoctors());
        model.addAttribute("medicineList", medicineService.getAllMedicines());
        model.addAttribute("diseaseList", diseaseService.getAllDiseases());
        model.addAttribute("tpList", treatmentPlanService.listAll());
        // Create Empty Prescription
        Prescription prescription = new Prescription();
        model.addAttribute("prescription", prescription);
        return "prescription/add_prescription";
    }

    @PostMapping("/save")
    public String savePrescription(@ModelAttribute("prescription") Prescription thePrescription, Model model,
            HttpSession session) {
        // Save Prescription
        prescriptionService.save(thePrescription);

        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        return "redirect:manage-prescription";
    }

    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("prescriptionId") int theID, Model model) {
        Prescription prescription = prescriptionService.findById(theID);
        model.addAttribute("prescription", prescription);
        model.addAttribute("patientList", userService.getAllPatients());
        model.addAttribute("doctorList", doctorService.getAllDoctors());
        model.addAttribute("medicineList", medicineService.getAllMedicines());
        model.addAttribute("diseaseList", diseaseService.getAllDiseases());
        model.addAttribute("tpList", treatmentPlanService.listAll());
        return "prescription/add_prescription";
    }

    @PostMapping("/accept")
    public String acceptPrescription(@RequestParam("prescriptionId") int theID, Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        prescriptionService.acceptById(theID);
        return "redirect:manage-prescription";
    }

    @PostMapping("/reject")
    public String rejectPrescription(@RequestParam("prescriptionId") int theID, Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        prescriptionService.rejectById(theID);
        return "redirect:manage-prescription";
    }

    @GetMapping("/manage-prescription")
    public String managePrescription(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        model.addAttribute("newPrescriptionList", prescriptionService.getNewPrescriptions());
        model.addAttribute("oldPrescriptionList", prescriptionService.getOldPrescriptions());
        // Other logic for the manage patient record page
        return "/prescription/manage_prescription";
    }

    @PostMapping("/deletePrescription")
    public String deletePrescription(@ModelAttribute("prescription") Prescription thePrescription, Model model,
            HttpSession session) {
        // Save Prescription
        prescriptionService.deletePrescription(thePrescription);

        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        return "redirect:manage-prescription";
    }
}
