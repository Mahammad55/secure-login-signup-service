package com.example.securityloginsignup.service;

import com.example.securityloginsignup.model.dto.request.LoginRequest;
import com.example.securityloginsignup.model.dto.request.RegisterRequest;
import com.example.securityloginsignup.model.dto.response.LoginResponse;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    void activate(String email, Integer verificationCode);

    void resendVerificationCode(String email);

    LoginResponse login(LoginRequest loginRequest);
}
