package com.example.demo.controller;

import java.util.List;
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
import com.example.demo.entity.Prescription;
import com.example.demo.entity.User;
import com.example.demo.service.PrescriptionService;
import com.example.demo.service.UserService;
import com.example.demo.service.MedicineService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController
{

    // load employee data
    private PrescriptionService prescriptionService;
    private UserService userService;
    private MedicineService medicineService;
    private List<Prescription> thePrescriptions;
    @Autowired

    public PrescriptionController(PrescriptionService prescriptionService,UserService userService, MedicineService medicineService)
    {
        this.prescriptionService = prescriptionService;
        this.userService = userService;
        this.medicineService = medicineService;
    }

    @GetMapping("/list")
    public String listMedicine(Model theModel)
    {
        thePrescriptions = prescriptionService.getAllPrescriptions();
        theModel.addAttribute("prescriptions", thePrescriptions);
        return "prescription/manage_prescription";
    }

    @GetMapping("/add-prescription")
    public String getPrescriptionForm(Model model, HttpSession session)
    {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        model.addAttribute("patientList", userService.getAllPatients());
        model.addAttribute("medicineList", medicineService.getAllMedicines());
        //Create Empty Prescription
        Prescription prescription = new Prescription();
        model.addAttribute("prescription",prescription);
        return "prescription/add_prescription";
    }

    @PostMapping("/save")
    public String savePrescription(@ModelAttribute("prescription") Prescription thePrescription, Model model,HttpSession session)
    {
        //Save Prescription
        prescriptionService.save(thePrescription);

        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        return "redirect:manage-prescription";
    }

    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("prescriptionId") int theID,Model model)
    {
        Prescription prescription = prescriptionService.findById(theID);
        model.addAttribute("prescription",prescription);
        model.addAttribute("patientList", userService.getAllPatients());
        model.addAttribute("medicineList", medicineService.getAllMedicines());
        return "prescription/add_prescription";
    }

    @GetMapping("/accept")
    public String acceptPrescription(@RequestParam("prescriptionId") int theID, Model model, HttpSession session)
    {
         // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        prescriptionService.acceptById(theID);
        return "redirect:manage-prescription";
    }

    @GetMapping("/reject")
    public String rejectPrescription(@RequestParam("prescriptionId") int theID, Model model, HttpSession session)
    {
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

}










