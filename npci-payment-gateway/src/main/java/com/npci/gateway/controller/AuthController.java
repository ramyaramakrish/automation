package com.npci.gateway.controller;

import com.npci.gateway.dto.ApiResponse;
import com.npci.gateway.dto.LoginRequest;
import com.npci.gateway.dto.LoginResponse;
import com.npci.gateway.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        // Simple authentication - in production use proper user service
        if ("testuser".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            LoginResponse response = new LoginResponse(token, request.getUsername());
            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
        }
        
        return ResponseEntity.status(401)
                .body(ApiResponse.error("Invalid credentials"));
    }
    
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            return ResponseEntity.ok(ApiResponse.success("Token is valid", username));
        }
        return ResponseEntity.status(401)
                .body(ApiResponse.error("Invalid token"));
    }
}
