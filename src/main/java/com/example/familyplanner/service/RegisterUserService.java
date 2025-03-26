package com.example.familyplanner.service;

import com.example.familyplanner.dto.requests.RegistrationRequest;
import com.example.familyplanner.dto.requests.UpdateProfileRequest;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.converter.UserConverter;
import com.example.familyplanner.service.exception.AlreadyExistException;
import com.example.familyplanner.service.exception.NotFoundException;
import com.example.familyplanner.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User newUser = converter.createUserFromDto(request);
        User savedUser = userRepository.save(newUser);

        return converter.createDtoFromUser(savedUser);
    }

    @Transactional
    public UserResponseDto updateUserProfile(String currentEmail, UpdateProfileRequest request) {

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("User with email " + currentEmail + " not found"));

        if (request.getEmail() != null && !request.getEmail().equals(currentEmail)) {
            if (validation.userExists(request.getEmail())) {
                throw new AlreadyExistException("Email " + request.getEmail() + " is already in use");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getAvatarId() != null) {
            user.setAvatarId(request.getAvatarId());
        }
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }

        User updatedUser = userRepository.save(user);

        return converter.createDtoFromUser(updatedUser);
    }
}




