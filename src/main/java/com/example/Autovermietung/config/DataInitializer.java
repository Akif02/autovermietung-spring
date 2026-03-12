package com.example.Autovermietung.config;

import com.example.Autovermietung.Entities.User;
import com.example.Autovermietung.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        

        if (!userRepository.existsByEmail("admin@test.de")) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@test.de");
            

            String encodedPassword = passwordEncoder.encode("admin123");
            admin.setPassword(encodedPassword);
            
            admin.setRole("ADMIN");
            admin.setBirthDate(LocalDate.of(1990, 1, 1));
            admin.setPhone("0123456789");
            admin.setAddress("Admin Street 1");
            admin.setHasDriverLicense(true);

            userRepository.save(admin);
            System.out.println("✅ ADMIN-USER WURDE ERSTELLT: admin@test.de / admin123");
        } else {
            System.out.println("ℹ️ Admin-User existiert bereits.");
        }
    }
}
