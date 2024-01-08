/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springRest.Controller;

import com.springRest.Throwable.PatientDiseaseAdminNotFoundException;
import com.springRest.enitity.Disease;
import com.springRest.enitity.Doctor;
import com.springRest.enitity.Patient;
import com.springRest.enitity.PatientDiseaseAdmin;
import com.springRest.service.DiseaseService;
import com.springRest.service.DoctorService;
import com.springRest.service.PatientDiseaseAdminService;
import com.springRest.service.PatientService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author RyderGan
 */
@Controller
@RequestMapping("/patientDiseases")
public class PatientDiseaseAdminController {

    private PatientDiseaseAdminService service;
    private DoctorService doctorService;
    private PatientService patientService;
    private DiseaseService diseaseService;

    public PatientDiseaseAdminController(PatientDiseaseAdminService service, DoctorService doctorService, PatientService patientService, DiseaseService diseaseService) {
        this.service = service;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.diseaseService = diseaseService;
    }

    @GetMapping("/list") //url from frontend
    public String showPDList(Model model) {
        List<PatientDiseaseAdmin> listPDs = service.getAllPatientDiseases();
        listPDs = service.listAllPDs(listPDs);
        model.addAttribute("listPDs", listPDs);
        return "patientDiseases/list-pd";
    }

    @GetMapping("/newPD")
    public String showNewPDForm(Model model) {
        model.addAttribute("patientDisease", new PatientDiseaseAdmin());
        List<Patient> patients = patientService.getAllPatients();
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Disease> diseases = diseaseService.getAllDiseases();
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
        model.addAttribute("diseases", diseases);
        model.addAttribute("pageTitle", "Add New Patient disease");
        return "patientDiseases/addPatientDiseases";
    }

    @PostMapping("/savePD")
    public String saveTP(@ModelAttribute("patientDisease") PatientDiseaseAdmin pd, RedirectAttributes ra) {
        service.save(pd);
        ra.addFlashAttribute("message", "A new PD has been added or changed");
        return "redirect:/patientDiseases/list";
    }

    @GetMapping("/editPD")
    public String showUpdatePDForm(@RequestParam("pdID") Integer pdID, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("patientDisease", service.findById(pdID));
            List<Patient> patients = patientService.getAllPatients();
            List<Doctor> doctors = doctorService.getAllDoctors();
            List<Disease> diseases = diseaseService.getAllDiseases();
            model.addAttribute("patients", patients);
            model.addAttribute("doctors", doctors);
            model.addAttribute("diseases", diseases);
            model.addAttribute("pageTitle", "Edit Patient Disease "+pdID);
            return "patientDiseases/editPD";
        } catch (PatientDiseaseAdminNotFoundException ex) {
            ra.addFlashAttribute("message", "This disease ID " + pdID + " not found");
            return "redirect:/patientDiseases/list";
        }
    }
    
    @PutMapping("/updatePD")
    public String updatePD(@RequestBody PatientDiseaseAdmin pd, RedirectAttributes ra){
        try {
            service.update(pd);
            //return message
            ra.addFlashAttribute("message", "TP " + pd.getId().toString() + " has been updated");
            return "redirect:/patientDiseases/list";
        } catch (PatientDiseaseAdminNotFoundException ex) {
            ra.addFlashAttribute("message", "This PD ID " + pd.getId() + " not found");
            return "redirect:/patientDiseases/list";
        }
    }

    @GetMapping("/deletePD/{id}")
    public String deleteTP(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("message", "The PD ID " + id + " has been deleted");
        } catch (PatientDiseaseAdminNotFoundException ex) {
            ra.addFlashAttribute("message", "This PD ID " + id + " not found");
        }
        return "redirect:/patientDiseases/list";
    }
}
