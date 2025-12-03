package com.example.library_shop.config;

import com.example.library_shop.entity.User;
import com.example.library_shop.enums.Role;
import com.example.library_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 🔍 Kiểm tra admin đã tồn tại chưa
        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = User.builder()
                    .fullName("Default Administrator")
                    .username("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("123456"))   // mật khẩu mặc định
                    .role(Role.ADMIN)       // ⭐ PHẢI LÀ ENUM
                    .build();

            userRepository.save(admin);
            System.out.println("=== DEFAULT ADMIN CREATED ===");

        } else {
            System.out.println("=== ADMIN ALREADY EXISTS ===");
        }
    }
}
