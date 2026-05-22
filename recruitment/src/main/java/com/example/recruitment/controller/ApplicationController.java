package com.example.recruitment.controller;

import com.example.recruitment.entity.Application;
import com.example.recruitment.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    // ✔ Constructor Injection
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    //  POST : CANDIDATE apply to JOB OFFER
    @PostMapping("/{candidateId}/{jobOfferId}")
    public Application apply(@PathVariable Long candidateId,
                             @PathVariable Long jobOfferId) {
        return applicationService.apply(candidateId, jobOfferId);
    }

    //  GET all applications
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    //  GET application by ID
    @GetMapping("/{id}")
    public Application getById(@PathVariable Long id) {
        return applicationService.getById(id);
    }

    //  UPDATE status (ADMIN / RECRUITER)
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                    @RequestParam String status) {
        return applicationService.updateStatus(id, status, null);
    }

    //  DELETE application
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        applicationService.delete(id, null);
    }
}