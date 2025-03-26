package com.example.familyplanner.controller;

import com.example.familyplanner.dto.RegistrationRequest;
import com.example.familyplanner.dto.TaskRequest;
import com.example.familyplanner.dto.UpdateProfileRequest;
import com.example.familyplanner.dto.UserResponseDto;

import com.example.familyplanner.service.FindUserService;
import com.example.familyplanner.service.RegisterUserService;
import com.example.familyplanner.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

//нужны тесты
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final FindUserService findService;
    private final RegisterUserService registerService;
    private final TaskService taskService;

    @Operation(
            summary = "Create user",
                description = "Create user. Store him in DataBase, and return in response only non confidential info",
                    responses = {   @ApiResponse(responseCode = "201", description = "User successfully created"),
                                    @ApiResponse(responseCode = "400", description = "User already exist"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                                    @ApiResponse(responseCode = "500", description = "Iternal Server Error")})
    @PostMapping
    public ResponseEntity<UserResponseDto> createNewUser(@Valid @RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(registerService.createNewUser(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Find all users",
                description = "Find all users that already stores in DataBase and return in response only non Confidential information",
                    responses = {   @ApiResponse(responseCode = "200", description = "All users found"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                                    @ApiResponse(responseCode = "500", description = "Iternal Server Error")})
    @GetMapping
    public List<UserResponseDto> findAll() {
        return findService.findAll();
    }


    @Operation(
            summary = "Find user by email",
                description = "Find user that already exist in DataBase via his unique and personal email",
                    responses = {   @ApiResponse(responseCode = "200", description = "All users found"),
                                    @ApiResponse(responseCode = "400", description = "Validation Error"),
                                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                                    @ApiResponse(responseCode = "404", description = "User not found"),
                                    @ApiResponse(responseCode = "500", description = "Iternal Server Error")})
    @GetMapping("/by-email/{email}")
    public UserResponseDto findUserByEmail(@PathVariable String email) {
        return findService.findUserByEmail(email);
    }

    @Operation(
            summary = "Update user profile",
            description = "Updates authenticated user's profile information",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Validation error or email already in use"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateUserProfile(
            Principal principal,
            @Valid @RequestBody UpdateProfileRequest request) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = principal.getName();
        UserResponseDto updatedUser = registerService.updateUserProfile(email, request);

        return ResponseEntity.ok(updatedUser);
    }
}

