package com.example.recruitment.repository;

import com.example.recruitment.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findByTitleContaining(String title);

    List<JobOffer> findByRequiredSkillsContaining(String skill);
}
