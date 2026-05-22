package com.example.recruitment.service;

import com.example.recruitment.entity.Application;
import com.example.recruitment.entity.JobOffer;
import com.example.recruitment.entity.User;
import com.example.recruitment.enums.ApplicationStatus;
import com.example.recruitment.enums.RoleName;
import com.example.recruitment.repository.ApplicationRepository;
import com.example.recruitment.repository.JobOfferRepository;
import com.example.recruitment.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobOfferRepository jobOfferRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              UserRepository userRepository,
                              JobOfferRepository jobOfferRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    //  APPLY
    @Transactional
    public Application apply(Long candidateId, Long jobOfferId) {

        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (candidate.getRole() != RoleName.CANDIDATE) {
            throw new RuntimeException("Only candidates can apply");
        }

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        if (applicationRepository.existsByCandidate_IdAndJobOffer_Id(candidateId, jobOfferId)) {
            throw new RuntimeException("Already applied");
        }

        Application application = new Application(candidate, jobOffer);

        return applicationRepository.save(application);
    }

    //  GET ALL (ANTI N+1 via EntityGraph)
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    //  GET BY ID
    public Application getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    //  UPDATE STATUS (SECURED)
    @Transactional
    public Application updateStatus(Long id, String status, User currentUser) {

        if (currentUser.getRole() != RoleName.ADMIN &&
            currentUser.getRole() != RoleName.RECRUITER) {
            throw new RuntimeException("Access denied");
        }

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        try {
            ApplicationStatus newStatus = ApplicationStatus.valueOf(status.toUpperCase());
            application.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }

        return application;
    }

    //  DELETE (SECURED)
    @Transactional
    public void delete(Long id, User currentUser) {

        if (currentUser.getRole() != RoleName.ADMIN) {
            throw new RuntimeException("Only admin can delete");
        }

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(application);
    }
}