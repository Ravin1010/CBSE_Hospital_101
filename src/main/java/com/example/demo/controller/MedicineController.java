package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.User;
import com.example.demo.service.MedicineService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pharmacy")
public class MedicineController{
    @Autowired
    private MedicineService medicineService;

    public MedicineController(MedicineService medicineService)
    {
        this.medicineService = medicineService;
    }

    @GetMapping("/add-medicine")
    public String getMedicineForm(Model model, HttpSession session)
    {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);

        //Create Empty Medicine
        Medicine medicine = new Medicine();
        model.addAttribute("medicine",medicine);
        return "pharmacy/add_medicine";
    }

    @PostMapping("/save")
    public String saveMedicine(@ModelAttribute("medicine") Medicine theMedicine, Model model,HttpSession session)
    {
        //Save Medicine
        medicineService.save(theMedicine);

        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        model.addAttribute("medicineList", medicineService.getAllMedicines());
        return "redirect:manage-pharmacy";
    }

    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("id") int theID,Model model)
    {
        Medicine medicine = medicineService.findById(theID);
        model.addAttribute("medicine",medicine);
        return "pharmacy/add_medicine";
    }

    @PostMapping("/delete")
    public String deleteMedicine(@RequestParam("id") int theID, Model model, HttpSession session)
    {
         // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        medicineService.deleteById(theID);
        return "redirect:manage-pharmacy";
    }

    @GetMapping("/manage-pharmacy")
    public String managePharmacyInventory(Model model, HttpSession session) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        // Pass user information to the model
        model.addAttribute("user", user);
        model.addAttribute("medicineList", medicineService.getAllMedicines());

        // Other logic for the manage patient record page
        return "/pharmacy/manage_pharmacy";
    }

}










