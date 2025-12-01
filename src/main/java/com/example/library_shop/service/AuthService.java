package com.example.library_shop.service;

import com.example.library_shop.dto.LoginRequest;
import com.example.library_shop.dto.LoginResponse;
import com.example.library_shop.dto.RegisterRequest;
import com.example.library_shop.dto.UserDTO;
import com.example.library_shop.entity.User;
import com.example.library_shop.repository.UserRepository;
import com.example.library_shop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // REGISTER
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User saved = userRepository.save(user);

        return UserDTO.builder()
                .id(saved.getId())
                .fullName(saved.getFullName())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }

    // LOGIN
    public LoginResponse login(String username, String rawPassword) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        String jwt = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return new LoginResponse(
                jwt,
                user.getRole().name(),
                user.getUsername()
        );
    }
}

