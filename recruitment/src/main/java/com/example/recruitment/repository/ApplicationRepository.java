package com.example.recruitment.repository;

import com.example.recruitment.entity.Application;
import com.example.recruitment.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    //  éviter double candidature
    boolean existsByCandidate_IdAndJobOffer_Id(Long candidateId, Long jobOfferId);

    @EntityGraph(attributePaths = {"candidate", "jobOffer"})
    List<Application> findAll();

    //  filtrage par statut
    @EntityGraph(attributePaths = {"candidate", "jobOffer"})
    List<Application> findByStatus(ApplicationStatus status);

    //  filtrage par candidat
    @EntityGraph(attributePaths = {"jobOffer"})
    List<Application> findByCandidate_Id(Long candidateId);

    //  filtrage par job
    @EntityGraph(attributePaths = {"candidate"})
    List<Application> findByJobOffer_Id(Long jobOfferId);

    //  pagination 
    @EntityGraph(attributePaths = {"candidate", "jobOffer"})
    Page<Application> findAll(Pageable pageable);
}