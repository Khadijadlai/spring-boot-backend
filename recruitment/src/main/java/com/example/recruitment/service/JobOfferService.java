package com.example.recruitment.service;

import com.example.recruitment.entity.JobOffer;
import com.example.recruitment.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    public JobOfferService(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    public JobOffer create(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    public List<JobOffer> getAll() {
        return jobOfferRepository.findAll();
    }

    public JobOffer getById(Long id) {
        return jobOfferRepository.findById(id)
                .orElseThrow();
    }

    public JobOffer update(Long id, JobOffer updatedOffer) {

        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow();

        jobOffer.setTitle(updatedOffer.getTitle());
        jobOffer.setDescription(updatedOffer.getDescription());
        jobOffer.setRequiredSkills(updatedOffer.getRequiredSkills());
        jobOffer.setSalary(updatedOffer.getSalary());

        return jobOfferRepository.save(jobOffer);
    }

    public void delete(Long id) {
        jobOfferRepository.deleteById(id);
    }

    public List<JobOffer> searchByTitle(String title) {
        return jobOfferRepository.findByTitleContaining(title);
    }

    public List<JobOffer> searchBySkill(String skill) {
        return jobOfferRepository.findByRequiredSkillsContaining(skill);
    }
}