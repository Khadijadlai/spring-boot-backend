package com.example.recruitment.config;

import com.example.recruitment.entity.User;
import com.example.recruitment.enums.RoleName;
import com.example.recruitment.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // 👑 ADMIN
        if (userRepository.findByEmail("admin@recruitment.com").isEmpty()) {

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@recruitment.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRole(RoleName.ADMIN);

            userRepository.save(admin);
        }

        // 👨‍💻 RECRUITER
        if (userRepository.findByEmail("recruiter@recruitment.com").isEmpty()) {

            User recruiter = new User();
            recruiter.setName("Recruiter");
            recruiter.setEmail("recruiter@recruitment.com");
            recruiter.setPassword(passwordEncoder.encode("1234"));
            recruiter.setRole(RoleName.RECRUITER);

            userRepository.save(recruiter);
        }

        // 👤 CANDIDATE
        if (userRepository.findByEmail("candidate@recruitment.com").isEmpty()) {

            User candidate = new User();
            candidate.setName("Candidate");
            candidate.setEmail("candidate@recruitment.com");
            candidate.setPassword(passwordEncoder.encode("1234"));
            candidate.setRole(RoleName.CANDIDATE);

            userRepository.save(candidate);
        }

        System.out.println("✅ Default users initialized successfully.");
    }
}