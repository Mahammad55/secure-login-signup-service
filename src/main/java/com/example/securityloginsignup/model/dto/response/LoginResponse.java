package com.example.securityloginsignup.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NotBlank
@AllArgsConstructor
public class LoginResponse {
    private String email;

    private String token;
}
