package com.example.demo.controller;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
import com.example.demo.service.DiseaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    // load employee data
    private DoctorService doctorService;
    private DiseaseService diseaseService;
    private List<Doctor> theDoctors;

    public DoctorController(DoctorService doctorService, DiseaseService diseaseService) {
        this.doctorService = doctorService;
        this.diseaseService = diseaseService;
    }

    @GetMapping("/list")
    public String listDoctors(Model theModel) {
        theDoctors = doctorService.getAllDoctors();
        theModel.addAttribute("doctors", theDoctors);
        // theModel.addAttribute("diseaseList",diseaseService.getAllDiseases());
        return "doctors/list-doctors";
    }

    @GetMapping("/addDoctor")
    public String getDoctorForm(Model model) {
        Doctor Doctor = new Doctor();
        model.addAttribute("diseaseList", diseaseService.getAllDiseases());
        model.addAttribute("doctor", Doctor);
        return "doctors/addDoctor";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute("doctor") Doctor theDoctor) {
        System.out.println("DOB: " + theDoctor.getDateOfBirth());

        doctorService.save(theDoctor);
        return "redirect:/doctors/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("doctorId") int theID, Model model) {
        Doctor doctor = doctorService.findById(theID);
        model.addAttribute("diseaseList", diseaseService.getAllDiseases());
        model.addAttribute("doctor", doctor);
        return "doctors/addDoctor";
    }

    @GetMapping("/delete")
    public String deleteDoctor(@RequestParam("doctorId") int theID) {
        doctorService.deleteById(theID);
        return "redirect:/doctors/list";
    }
}
