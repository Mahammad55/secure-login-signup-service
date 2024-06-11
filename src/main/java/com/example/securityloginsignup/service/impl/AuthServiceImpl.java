package com.example.securityloginsignup.service.impl;

import com.example.securityloginsignup.exception.AccountAlreadyExistException;
import com.example.securityloginsignup.exception.AccountNotFoundException;
import com.example.securityloginsignup.exception.VerificationException;
import com.example.securityloginsignup.mail.MailService;
import com.example.securityloginsignup.mapper.UserMapper;
import com.example.securityloginsignup.model.dto.request.LoginRequest;
import com.example.securityloginsignup.model.dto.request.RegisterRequest;
import com.example.securityloginsignup.model.dto.response.LoginResponse;
import com.example.securityloginsignup.model.entity.Authority;
import com.example.securityloginsignup.model.entity.User;
import com.example.securityloginsignup.repository.UserRepository;
import com.example.securityloginsignup.security.JwtService;
import com.example.securityloginsignup.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest registerRequest) {
        userRepository.findUserByUsername(registerRequest.getEmail()).ifPresent(user -> {
                    throw new AccountAlreadyExistException("User already exist with email: " + user.getUsername());
                }
        );

        User user = userMapper.requestToEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(new Random().nextInt(1000, 10000));
        user.setAuthorities(List.of(new Authority("USER")));
        userRepository.save(user);
        mailService.sendEmail(user.getUsername(), user.getVerificationCode());
    }

    @Override
    public void activate(String email, Integer verificationCode) {
        User user = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new AccountNotFoundException("User not found with email: " + email));

        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new VerificationException("Incorrect verification code");
        }

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }

    @Override
    public void resendVerificationCode(String email) {
        User user = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new AccountNotFoundException("User not found with email: " + email));

        user.setVerificationCode(new Random().nextInt(1000, 10000));
        userRepository.save(user);
        mailService.sendEmail(email, user.getVerificationCode());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByUsername(loginRequest.getEmail())
                .orElseThrow(() -> new AccountNotFoundException("User not found with email: " + loginRequest.getEmail()));

        if (!user.isEnabled()) {
            throw new VerificationException("Account didn't activated");
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        return new LoginResponse(loginRequest.getEmail(), jwtService.generateAccessToken(principal));
    }
}
