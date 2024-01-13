package com.example.demo.controller;

import com.example.demo.Throwable.DiseaseNotFoundException;
import com.example.demo.entity.Disease;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.TreatmentPlan;
import com.example.demo.service.DiseaseService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.service.TreatmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/diseases")
public class DiseaseController {

    // load employee data
    private DoctorService doctorService;
    private PatientService patientService;
    private DiseaseService diseaseService;
    private TreatmentPlanService tpService;
    
    private List<Disease> theDiseases;

    public DiseaseController(DoctorService doctorService, PatientService patientService, DiseaseService diseaseService, TreatmentPlanService tpService) {
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
    public String showUpdateForm(@RequestParam("diseaseId") int theID, Model model, RedirectAttributes ra){
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

    @Autowired
    @PersistenceContext
    private EntityManager em;

    @GetMapping("/delete")
    @Transactional
    public String deleteDoctor(@RequestParam("diseaseId") int theID, RedirectAttributes ra) {

        Disease a = em.find(Disease.class, theID);
        for (Doctor b : a.getDoctors()) {
            if (b.getDisease() != null) {
                em.remove(a);
                ra.addFlashAttribute("message", "The disease ID " + theID + " has been deleted");
            }
        }
        em.remove(a);
        //diseaseService.deleteById(theID);
        return "redirect:/diseases/list";
    }
}
