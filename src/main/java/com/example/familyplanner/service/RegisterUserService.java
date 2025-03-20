package com.example.familyplanner.service;

import com.example.familyplanner.dto.RegistrationRequest;
import com.example.familyplanner.dto.UserResponseDto;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.RoleRepository;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.converter.UserConverter;
import com.example.familyplanner.service.exception.AlreadyExistException;
import com.example.familyplanner.service.exception.ValidationException;
import com.example.familyplanner.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final ValidationService validation;


    public UserResponseDto createNewUser(RegistrationRequest request) {
        if (validation.userExists(request.getEmail())) {
            throw new AlreadyExistException("User with email " + request.getEmail() + " already exists");
        }

        // Perform additional validation if needed
        if (!isValidPassword(request.getPassword())) {
            throw new ValidationException("Password does not meet security requirements");
        }

        User newUser = converter.createUserFromDto(request);
        User savedUser = userRepository.save(newUser);

        return converter.createDtoFromUser(savedUser);
    }

    private boolean isValidPassword(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.length() <= 25 &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }


}
