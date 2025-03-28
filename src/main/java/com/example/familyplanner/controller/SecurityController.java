package com.example.familyplanner.controller;

import com.example.familyplanner.Security.JWT.JwtCore;
import com.example.familyplanner.dto.requests.LoginRequest;
import com.example.familyplanner.dto.requests.PasswordResetRequest;
import com.example.familyplanner.dto.requests.RegistrationRequest;
import com.example.familyplanner.dto.requests.ResetPasswordRequest;
import com.example.familyplanner.dto.responses.*;
import com.example.familyplanner.service.FindUserService;
import com.example.familyplanner.service.PasswordResetService;
import com.example.familyplanner.service.RegisterUserService;
import com.example.familyplanner.service.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication and password recovery")
public class SecurityController {


    //Возрващать DTO
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final RegisterUserService registerUserService;
    private final FindUserService findUserService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/sign-in")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticates user and returns JWT token along with user info")
  
        public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtCore.createToken(loginRequest.getEmail());

        // Get user information
        UserResponseDto userDto = findUserService.findUserByEmail(loginRequest.getEmail());

        // Create response
        AuthResponseDto responseDto = AuthResponseDto.builder()
                .token(jwt)
                .user(userDto)
                .email(loginRequest.getEmail())
                .message("Login successful")
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/sign-up")
    @Operation(
            summary = "Register new user",
            description = "Registers a new user and returns JWT token along with user info"
    )
  
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegistrationRequest request) {
        // Create new user
        UserResponseDto newUser = registerUserService.createNewUser(request);

        // Generate JWT token
        String jwt = jwtCore.createToken(request.getEmail());

        // Create response
        RegisterResponseDto responseDto = RegisterResponseDto.builder()
                .user(newUser)
                .token(jwt)
                .message("User successfully registered")
                .status("success")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/forgot-password")
    @Operation(
            summary = "Request password reset",
            description = "Sends a 6-digit code to the user's email for password reset"
    )
    public ResponseEntity<PasswordResetRequestResponseDto> forgotPassword(@Valid @RequestBody PasswordResetRequest request) {
        try {
            passwordResetService.sendResetToken(request.getEmail());
        } catch (NotFoundException e) {
            // Don't reveal if email exists or not (security best practice)
        }

        PasswordResetRequestResponseDto responseDto = PasswordResetRequestResponseDto.builder()
                .message("If your email is registered, a password reset code has been sent.")
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/reset-password")
    @Operation(
            summary = "Reset password",
            description = "Resets password using the provided token and new password"
    )
    public ResponseEntity<PasswordResetResponseDto> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        // Let the service method handle the validation and throw exceptions if needed
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword(), request.getConfirmPassword());

        // If we reach here, password reset was successful
        PasswordResetResponseDto responseDto = PasswordResetResponseDto.builder()
                .message("Your password has been reset successfully")
                .build();

        return ResponseEntity.ok(responseDto);
    }
}