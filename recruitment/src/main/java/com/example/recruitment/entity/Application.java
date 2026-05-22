package com.example.recruitment.entity;

import com.example.recruitment.enums.ApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // date de candidature
    private LocalDate appliedDate;

    //  statut candidature
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    //  candidat (User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User candidate;

    //  offre d'emploi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id", nullable = false)
    private JobOffer jobOffer;

    // 🟢constructeur par défaut
    public Application() {
        this.appliedDate = LocalDate.now();
        this.status = ApplicationStatus.PENDING;
    }

    //  constructeur complet
    public Application(User candidate, JobOffer jobOffer) {
        this.candidate = candidate;
        this.jobOffer = jobOffer;
        this.appliedDate = LocalDate.now();
        this.status = ApplicationStatus.PENDING;
    }

    //  getters & setters

    public Long getId() {
        return id;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
}