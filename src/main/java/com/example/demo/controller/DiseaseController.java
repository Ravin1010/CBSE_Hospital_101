package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Throwable.DiseaseNotFoundException;
import com.example.demo.entity.Disease;
import com.example.demo.entity.TreatmentPlan;
import com.example.demo.service.DiseaseService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientDiseaseAdminService;
import com.example.demo.service.TreatmentPlanService;

@Controller
@RequestMapping("/diseases")
public class DiseaseController {

    // load employee data
    private DoctorService doctorService;
    private PatientDiseaseAdminService patientService;
    private DiseaseService diseaseService;
    private TreatmentPlanService tpService;

    private List<Disease> theDiseases;

    public DiseaseController(DoctorService doctorService, PatientDiseaseAdminService patientService,
            DiseaseService diseaseService, TreatmentPlanService tpService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.diseaseService = diseaseService;
        this.tpService = tpService;
    }

    @GetMapping("/diseaseModule")
    public String listDoctors() {
        return "disease/diseaseModule";
    }

    @GetMapping("/list")
    public String listDoctors(Model theModel) {
        theModel.addAttribute("diseaseList", diseaseService.getAllDiseases());
        return "disease/list-disease";
    }

    @GetMapping("/addDisease")
    public String getDoctorForm(Model model) {
        Disease disease = new Disease();
        List<TreatmentPlan> listTPs = tpService.listAll();
        model.addAttribute("listTPs", listTPs);
        model.addAttribute("pageTitle", "Add New Disease");
        model.addAttribute("disease", disease);
        return "disease/addDisease";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute("disease") Disease disease, RedirectAttributes ra) {
        diseaseService.save(disease);
        ra.addFlashAttribute("message", "A new disease has been added or changed");
        return "redirect:/diseases/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("diseaseId") int theID, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("disease", diseaseService.findById(theID));
            List<TreatmentPlan> listTPs = tpService.listAll();
            model.addAttribute("listTPs", listTPs);
            model.addAttribute("pageTitle", "Edit Disease");
            return "disease/addDisease";
        } catch (DiseaseNotFoundException ex) {
            ra.addFlashAttribute("message", "This disease ID " + theID + " not found");
            return "redirect:/diseases/list";
        }
    }

    @GetMapping("/delete")
    @jakarta.transaction.Transactional
    public String deleteDoctor(@RequestParam("diseaseId") int id, RedirectAttributes ra) {

        try {
            diseaseService.deleteById(id);
            ra.addFlashAttribute("message", "The PD ID " + id + " has been deleted");
        } catch (DiseaseNotFoundException ex) {
            ra.addFlashAttribute("message", "This PD ID " + id + " not found");
        }
        return "redirect:/diseases/list";
    }
}
