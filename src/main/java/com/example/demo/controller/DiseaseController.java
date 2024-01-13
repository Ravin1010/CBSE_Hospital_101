package com.springRest.Controller;

import com.springRest.Throwable.DiseaseNotFoundException;
import com.springRest.Throwable.PatientDiseaseAdminNotFoundException;
import com.springRest.enitity.Disease;
import com.springRest.enitity.Doctor;
import com.springRest.enitity.TreatmentPlan;
import com.springRest.service.DiseaseService;
import com.springRest.service.DoctorService;
import com.springRest.service.PatientService;
import com.springRest.service.TreatmentPlanService;
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

    @GetMapping("/delete")
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
