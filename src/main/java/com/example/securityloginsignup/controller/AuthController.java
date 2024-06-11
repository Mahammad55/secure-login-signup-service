package com.example.securityloginsignup.controller;

import com.example.securityloginsignup.model.dto.request.LoginRequest;
import com.example.securityloginsignup.model.dto.request.RegisterRequest;
import com.example.securityloginsignup.model.dto.response.LoginResponse;
import com.example.securityloginsignup.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activate(@RequestParam String email, @RequestParam Integer verificationCode) {
        authService.activate(email, verificationCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resendVerificationCode")
    public ResponseEntity<Void> resendVerificationCode(@RequestParam("email") String email) {
        authService.resendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
