package com.example.demo.controller;

import com.example.demo.entity.Payment;
import com.example.demo.entity.Prescription;
import com.example.demo.entity.User;
import com.example.demo.service.MedicineService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.PrescriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


//    @GetMapping("/manage-patient-prescription")
//    public String managePrescription(Model model, HttpSession session) {
//        // Retrieve user details from the session
//        User user = (User) session.getAttribute("user");
//
//        // Pass user information to the model
//        model.addAttribute("user", user);
//        model.addAttribute("acceptedPrescriptionList", prescriptionService.getNewPrescriptionsById(user.getUser_id()));
//        model.addAttribute("collectedPrescriptionList", prescriptionService.getOldPrescriptionsById(user.getUser_id()));
//        // Other logic for the manage patient record page
//        return "/patient_prescription/manage_patient_prescription";
//    }

    @GetMapping("/manage-patient-prescription")
    public String managePrescription(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);

        // Get new and old prescriptions by user ID
        List<Prescription> acceptedPrescriptions = prescriptionService.getNewPrescriptionsById(user.getUser_id());
        List<Prescription> collectedPrescriptions = prescriptionService.getOldPrescriptionsById(user.getUser_id());

        // Add payment details for each collected prescription
        Map<Integer, List<Payment>> paymentMap = new HashMap<>();
        for (Prescription prescription : collectedPrescriptions) {
            List<Payment> payments = paymentService.findByPrescriptionId(prescription.getPrescriptionId());
            paymentMap.put(prescription.getPrescriptionId(), payments);
        }

        // Add attributes to the model
        model.addAttribute("acceptedPrescriptionList", acceptedPrescriptions);
        model.addAttribute("collectedPrescriptionList", collectedPrescriptions);
        model.addAttribute("paymentMap", paymentMap);

        // Return the view
        return "/patient_prescription/manage_patient_prescription";
    }



    @GetMapping("/collect/{prescriptionId}")
    public String collectPrescription(@PathVariable Integer prescriptionId, Model model) {
        Payment payment = prescriptionService.createPayment(prescriptionId);
        model.addAttribute("payment", payment);
        return "payment"; // This will redirect to payment.html with the payment data
    }

//    @PostMapping("/pay/{paymentId}")
//    public String payPrescription(@PathVariable Integer paymentId, RedirectAttributes redirectAttributes) {
//        prescriptionService.updatePaymentStatus(paymentId, "paid");
//        redirectAttributes.addFlashAttribute("successMessage", "Payment successful");
//        return "redirect:/patient-prescription/manage-patient-prescription";
//    }

    @Autowired
    private PaymentService paymentService;
    @PostMapping("/pay/{paymentId}")
    public String payPrescription(@PathVariable Integer paymentId, RedirectAttributes redirectAttributes) {
        try {
            Payment payment = paymentService.findById(paymentId);
            Prescription prescription = prescriptionService.findById(payment.getPrescriptionId());

            if (prescription == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Prescription not found");
                return "redirect:/patient-prescription/manage-patient-prescription";
            }

            if (medicineService.collectMedicine(prescription.getMedicineId(), prescription.getMedicineQuantity())) {
                prescriptionService.collectById(prescription.getPrescriptionId());
                paymentService.updatePaymentStatus(paymentId, "PAID");
                redirectAttributes.addFlashAttribute("successMessage", "Payment and Medicine Dispense Successful");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Medicine Stock in Pharmacy is insufficient. Please try again later");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred: " + e.getMessage());
        }

        return "redirect:/patient-prescription/manage-patient-prescription";
    }




}










