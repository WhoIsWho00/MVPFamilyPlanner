package com.example.familyplanner.controller;

import com.example.familyplanner.Security.JWT.JwtCore;
import com.example.familyplanner.dto.ResetPasswordRequest;
import com.example.familyplanner.dto.requests.*;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.service.FindUserService;
import com.example.familyplanner.service.PasswordResetService;
import com.example.familyplanner.service.RegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API for user authentication and password recovery")
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final RegisterUserService registerUserService;
    private final FindUserService findUserService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public SecurityController(AuthenticationManager authenticationManager,
                              JwtCore jwtCore,
                              RegisterUserService registerUserService,
                              FindUserService findUserService,
                              PasswordResetService passwordResetService) {
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
        this.registerUserService = registerUserService;
        this.findUserService = findUserService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Login", description = "Authenticate and get JWT")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtCore.createToken(loginRequest.getEmail());
        UserResponseDto userDto = findUserService.findUserByEmail(loginRequest.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("user", userDto);
        response.put("email", loginRequest.getEmail());
        response.put("message", "Login successful");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Register", description = "Create new user account")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        UserResponseDto newUser = registerUserService.createNewUser(request);
        String jwt = jwtCore.createToken(request.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("user", newUser);
        response.put("token", jwt);
        response.put("message", "User successfully registered");
        response.put("status", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot Password", description = "Send password reset link")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody PasswordResetRequest request) {
        passwordResetService.sendResetToken(request.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("message", " Reqeszt send on Email");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset Password", description = "Set a new password using the reset token")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Check,password changed");

        return ResponseEntity.ok(response);
    }
}
