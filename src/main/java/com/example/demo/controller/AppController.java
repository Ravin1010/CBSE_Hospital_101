package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.PatientDisease;
import com.example.demo.entity.User;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PatientDiseaseRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PatientDiseaseRepository patientDiseaseRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "app/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "app/signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // add logic to check if email is unique, if not return error
        if (userRepo.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email is already in use");
            return "app/signup_form"; // Return to the registration form with an error message
        } else {
            userRepo.save(user);
        }

        return "app/register_success";
    }

    @GetMapping("/login")
    public String login() {
        return "app/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, HttpSession session, @RequestParam String password, Model model) {
        // Retrieve user from the database
        User user = userRepo.findByEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            // Successful login

            // Store user details in the session
            session.setAttribute("user", user);

            // Check user role and redirect accordingly
            if (user.getRole().equals("patient")) {
                return "redirect:/user/dashboard";
            } else if (user.getRole().equals("admin")) {
                return "redirect:/admin/dashboard";
            } else {
                // Handle other roles or scenarios
                return "redirect:/login";
            }
        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password");
            return "app/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // Manually invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Add a flash attribute for the logout message
        redirectAttributes.addFlashAttribute("app/logout", true);

        // Redirect to the login page or any other page
        return "redirect:/login";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Pass user information to the model
            model.addAttribute("name", user.getName()); // Adjust this according to your User entity
            return "app/user_dashboard";
        } else {
            // Handle the case when user credentials are not available
            return "redirect:/login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        // Retrieve user details from the session
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Pass user information to the model
            model.addAttribute("name", user.getName()); // Adjust this according to your User entity
            return "app/admin_dashboard";
        } else {
            // Handle the case when user credentials are not available
            return "redirect:/login";
        }
    }

}
