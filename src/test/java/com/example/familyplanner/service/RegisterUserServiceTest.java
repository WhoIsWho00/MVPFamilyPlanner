package com.example.familyplanner.service;

import com.example.familyplanner.dto.requests.RegistrationRequest;
import com.example.familyplanner.dto.requests.UpdateProfileRequest;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.converter.UserConverter;
import com.example.familyplanner.service.exception.AlreadyExistException;
import com.example.familyplanner.service.exception.NotFoundException;
import com.example.familyplanner.service.validation.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter converter;

    @Mock
    private ValidationService validationService;

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);

    @InjectMocks
    private RegisterUserService registerUserService;

    private RegistrationRequest registrationRequest;
    private User newUser;
    private UserResponseDto userResponseDto;
    private UpdateProfileRequest updateProfileRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("Password!123");

        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setRoleName("USER");

        newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setUsername("testuser");
        newUser.setEmail("test@example.com");
        newUser.setPassword("encodedPassword");
        newUser.setRole(role);

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(newUser.getId());
        userResponseDto.setUsername("testuser");
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setRole(role);

        updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setUsername("updateduser");
        updateProfileRequest.setEmail("updated@example.com");
        updateProfileRequest.setAvatarId("avatar1");
        updateProfileRequest.setAge(30);
    }

    @Test
    void createNewUser_ShouldCreateUserWhenEmailDoesNotExist() {

        when(validationService.userExists(anyString())).thenReturn(false);
        when(converter.createUserFromDto(any(RegistrationRequest.class))).thenReturn(newUser);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(converter.createDtoFromUser(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto result = registerUserService.createNewUser(registrationRequest, mockRequest);

        assertNotNull(result);
        assertEquals(userResponseDto.getId(), result.getId());
        assertEquals(userResponseDto.getUsername(), result.getUsername());
        assertEquals(userResponseDto.getEmail(), result.getEmail());

        verify(validationService).userExists("test@example.com");
        verify(converter).createUserFromDto(registrationRequest);
        verify(userRepository).save(newUser);
        verify(converter).createDtoFromUser(newUser);
    }

    @Test
    void createNewUser_ShouldThrowExceptionWhenEmailExists() {

        when(validationService.userExists(anyString())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () ->
                registerUserService.createNewUser(registrationRequest, mockRequest)
        );

        verify(validationService).userExists("test@example.com");
        verify(converter, never()).createUserFromDto(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserProfile_ShouldUpdateUserWhenUserExists() {

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(newUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(converter.createDtoFromUser(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto result = registerUserService.updateUserProfile("test@example.com", updateProfileRequest);

        assertNotNull(result);
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).save(newUser);
        verify(converter).createDtoFromUser(newUser);
    }

    @Test
    void updateUserProfile_ShouldThrowExceptionWhenUserDoesNotExist() {

        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                registerUserService.updateUserProfile("nonexistent@example.com", updateProfileRequest)
        );

        verify(userRepository).findByEmail("nonexistent@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserProfile_ShouldThrowExceptionWhenNewEmailAlreadyExists() {

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(newUser));
        when(validationService.userExists("updated@example.com")).thenReturn(true);

        assertThrows(AlreadyExistException.class, () ->
                registerUserService.updateUserProfile("test@example.com", updateProfileRequest)
        );

        verify(userRepository).findByEmail("test@example.com");
        verify(validationService).userExists("updated@example.com");
        verify(userRepository, never()).save(any());
    }
}