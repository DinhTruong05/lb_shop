package com.example.library_shop.controller;

import com.example.library_shop.dto.LoginRequest;
import com.example.library_shop.dto.LoginResponse;
import com.example.library_shop.dto.RegisterRequest;
import com.example.library_shop.dto.UserDTO;
import com.example.library_shop.entity.User;
import com.example.library_shop.repository.UserRepository;
import com.example.library_shop.security.JwtUtil;
import com.example.library_shop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    // ============== REGISTER ==================
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println("🔥 REQUEST BODY: " + request);
        return ResponseEntity.ok(authService.register(request));
    }

    // ============== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse response = authService.login(
                req.getUsername(),
                req.getPassword()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .build();

        return ResponseEntity.ok(dto);
    }


}
