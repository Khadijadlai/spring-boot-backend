package com.example.recruitment.controller;

import com.example.recruitment.repository.JobOfferRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final JobOfferRepository jobOfferRepository;

    public HomeController(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("jobs", jobOfferRepository.findAll());

        return "index";
    }
    @GetMapping("/login")
public String loginPage() {
    return "login";
}

@GetMapping("/register")
public String registerPage() {
    return "register";
}

@GetMapping("/add-job")
public String addJobPage() {
    return "add-job";
}
}