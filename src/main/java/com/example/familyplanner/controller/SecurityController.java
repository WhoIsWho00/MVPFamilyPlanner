package com.example.familyplanner.controller;

import com.example.familyplanner.Security.JWT.JwtCore;
import com.example.familyplanner.dto.requests.LoginRequest;
import com.example.familyplanner.dto.requests.RegistrationRequest;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.FindUserService;
import com.example.familyplanner.service.RegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API for user authentication and registration")
public class SecurityController {
    //Зачем дважды делать токены?

//1.После регистрации пользователь автоматически получает токен, чтобы не заставлять его сразу же входить в систему вручную
//2.При логине токен тоже нужен, потому что пользователь мог выйти из системы и теперь заново входит.


    //Возрващать DTO
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final RegisterUserService registerUserService;
    private final FindUserService findUserService;

    @Autowired
    public SecurityController(
                              AuthenticationManager authenticationManager,
                              JwtCore jwtCore,
                              RegisterUserService registerUserService,
                              FindUserService findUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
        this.registerUserService = registerUserService;
        this.findUserService = findUserService;
    }


    @Operation(
            summary = "Authenticate user",
            description = "Authenticates user and returns JWT token along with user info",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful login",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "token": "jwt-token-string",
                                              "user": {
                                                "id": 1,
                                                "username": "john_doe",
                                                "email": "john@example.com"
                                              },
                                              "email": "john@example.com",
                                              "message": "Login successful"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "400", description = "Invalid request (missing email or password)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Missing email or password.",
                                              "path": "/api/auth/sign-in"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 401,
                                              "error": "Unauthorized",
                                              "message": "Invalid email or password.",
                                              "path": "/api/auth/sign-in"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 500,
                                              "error": "Internal Server Error",
                                              "message": "An unexpected error occurred.",
                                              "path": "/api/auth/sign-in"
                                            }
                                            """
                            )))
            }
    )
    @PostMapping("/sign-in")

    //Возрващать DTO
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
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


    @Operation(
            summary = "Register new user",
            description = "Registers a new user and returns JWT token along with user info",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "user": {
                                                "id": 2,
                                                "username": "new_user",
                                                "email": "new_user@example.com"
                                              },
                                              "token": "jwt-token-string",
                                              "message": "User successfully registered",
                                              "status": "success"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "400", description = "User already exists or validation failed",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": {
                                                "email": "User with this email already exists or email is invalid.",
                                                "username": "Username already taken or invalid."
                                              },
                                              "path": "/api/auth/sign-up"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "403", description = "Forbidden (access denied)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 403,
                                              "error": "Forbidden",
                                              "message": "Access denied. Insufficient permissions.",
                                              "path": "/api/auth/sign-up"
                                            }
                                            """
                            ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-03-25T16:26:19.597Z",
                                              "status": 500,
                                              "error": "Internal Server Error",
                                              "message": "An unexpected error occurred.",
                                              "path": "/api/auth/sign-up"
                                            }
                                            """
                            )))
            }
    )
    @PostMapping("/sign-up")

    //Возрващать DTO
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
}