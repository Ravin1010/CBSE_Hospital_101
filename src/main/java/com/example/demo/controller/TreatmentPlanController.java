/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Throwable.TreatmentPlanNotFoundException;
import com.example.demo.entity.PatientDisease;
import com.example.demo.entity.TreatmentPlan;
import com.example.demo.service.TreatmentPlanService;

import jakarta.transaction.Transactional;

/**
 *
 * @author RyderGan
 */
@Controller
@RequestMapping("/treatment_plans")
public class TreatmentPlanController {

    private TreatmentPlanService service;

    public TreatmentPlanController(TreatmentPlanService service) {
        this.service = service;
    }

    @GetMapping("/list") // url from frontend
    public String showTreatmentPlansList(Model model) {
        List<TreatmentPlan> listTreatmentPlans = service.listAll();
        List<TreatmentPlan> listCustomTPs = service.listAllMedicines(listTreatmentPlans);
        model.addAttribute("listTreatmentPlans", listCustomTPs);
        // model.addAttribute("listMedicines", listMedicines);
        return "treatmentPlan/list-tp";
    }

    @GetMapping("/newTP")
    public String showNewTPForm(Model model) {
        model.addAttribute("treatment_plan", new TreatmentPlan());
        model.addAttribute("pageTitle", "Add New Treatment Plan");
        return "treatmentPlan/addTP";
    }

    @PostMapping("/saveTP")
    public String saveTP(@ModelAttribute("treatment_plan") TreatmentPlan tp, RedirectAttributes ra) {
        // Insert treatment plan
        // List<Integer> medicines = new ArrayList<>();
        // medicines.add(1);
        // medicines.add(2);
        // tp.setMedicine_ids(medicines);
        // List<Integer> medicines_amount = new ArrayList<>();
        // medicines_amount.add(1);
        // medicines_amount.add(1);
        // tp.setMedicines_amount(medicines_amount);
        // TreatmentPlan tp = new TreatmentPlan();
        service.save(tp);
        // return message
        ra.addFlashAttribute("message", "A new treatment plan has been added");
        return "redirect:/treatment_plans/list";
    }

    @GetMapping("/editTP")
    public String showEditTPForm(@RequestParam("tpID") Integer tpID, Model model, RedirectAttributes ra) {
        try {
            TreatmentPlan tp = service.findById(tpID);
            model.addAttribute("treatment_plan", tp);
            model.addAttribute("pageTitle", "Edit TP (ID: " + tpID.toString() + ")");
            return "treatmentPlan/editTP";
        } catch (TreatmentPlanNotFoundException ex) {
            ra.addFlashAttribute("message", "This TP ID " + tpID + " not found");
            return "redirect:/treatment_plans/list";
        }
    }

    @PostMapping("/updateTP/{id}")
    public String updateTP(@PathVariable("id") Integer id, @ModelAttribute("treatment_plan") TreatmentPlan tp,
            RedirectAttributes ra) {
        tp.setTpID(id);
        try {
            service.update(tp);
            ra.addFlashAttribute("message", "TP " + tp.getTpID().toString() + " has been updated");
            return "redirect:/treatment_plans/list";
        } catch (TreatmentPlanNotFoundException ex) {
            ra.addFlashAttribute("message", "This TP ID " + tp.getTpID() + " not found");
            return "redirect:/treatment_plans/editTP/" + tp.getTpID();
        }
    }

    @GetMapping("/deleteTP/{id}")
    public String deleteTP(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The TP ID " + id + " has been deleted");
        } catch (TreatmentPlanNotFoundException ex) {
            ra.addFlashAttribute("message", "This TP ID " + id + " not found");
        }
        return "redirect:/treatment_plans/list";
    }
}
