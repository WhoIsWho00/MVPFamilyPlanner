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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {



        @Mock
        private UserRepository userRepository;

//        @Mock
//        private RoleRepository roleRepository;

        @Mock
        private UserConverter converter;

        @Mock
        private ValidationService validation;

        @InjectMocks
        private RegisterUserService register;

        @BeforeEach
        void setUp(){
            MockitoAnnotations.openMocks(this);
        }

    @Test
    void testAlreadyExistsExceptionIfUserIsAlreadyExists() {

        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");

        when(validation.userExists("test@example.com")).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> register.createNewUser(request));

        verify(validation, times(1)).userExists("test@example.com");
        verifyNoMoreInteractions(userRepository, converter);
    }

    @Test
    void testThrowValidationExceptionIfPasswordInvalid() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");
        request.setPassword("123"); // слишком короткий и без спецсимволов

        when(validation.userExists("test@example.com")).thenReturn(false);

        assertThrows(ValidationException.class, () -> register.createNewUser(request));

        verify(validation, times(1)).userExists("test@example.com");
        verifyNoMoreInteractions(userRepository, converter);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");
        request.setPassword("ValidPass!23");

        User user = new User();
        User savedUser = new User();
        UUID userId = UUID.randomUUID();
        savedUser.setId(userId);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(userId);
        responseDto.setEmail("test@example.com");

        when(validation.userExists("test@example.com")).thenReturn(false);
        when(converter.createUserFromDto(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(converter.createDtoFromUser(savedUser)).thenReturn(responseDto);

        UserResponseDto result = register.createNewUser(request);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("test@example.com", result.getEmail());

        verify(validation, times(1)).userExists("test@example.com");
        verify(converter, times(1)).createUserFromDto(request);
        verify(userRepository, times(1)).save(user);
        verify(converter, times(1)).createDtoFromUser(savedUser);
    }
}