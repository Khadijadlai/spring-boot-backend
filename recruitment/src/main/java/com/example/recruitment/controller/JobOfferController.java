package com.example.recruitment.controller;

import com.example.recruitment.entity.JobOffer;
import com.example.recruitment.service.JobOfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joboffers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping
    public JobOffer create(@RequestBody JobOffer jobOffer) {
        return jobOfferService.create(jobOffer);
    }

    @GetMapping
    public List<JobOffer> getAll() {
        return jobOfferService.getAll();
    }

    @GetMapping("/{id}")
    public JobOffer getById(@PathVariable Long id) {
        return jobOfferService.getById(id);
    }

    @PutMapping("/{id}")
    public JobOffer update(@PathVariable Long id,
                           @RequestBody JobOffer jobOffer) {

        return jobOfferService.update(id, jobOffer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jobOfferService.delete(id);
    }

    @GetMapping("/search/title")
    public List<JobOffer> searchByTitle(@RequestParam String keyword) {

        return jobOfferService.searchByTitle(keyword);
    }

    @GetMapping("/search/skill")
    public List<JobOffer> searchBySkill(@RequestParam String skill) {

        return jobOfferService.searchBySkill(skill);
    }
}