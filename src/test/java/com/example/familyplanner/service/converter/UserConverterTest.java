package com.example.familyplanner.service.converter;

import com.example.familyplanner.dto.requests.RegistrationRequest;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.RoleRepository;
import com.example.familyplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserConverterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserConverter userConverter;

    private User user;
    private RegistrationRequest registrationRequest;
    private Role role;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();

        role = new Role();
        role.setId(UUID.randomUUID());
        role.setRoleName("USER");

        user = new User();
        user.setId(userId);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(role);
        user.setAvatarId("avatar1");
        user.setAge(25);

        registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("Password!123");
        registrationRequest.setAvatarId("avatar1");
        registrationRequest.setAge(25);
    }

    @Test
    void createDtoFromUser_ShouldConvertUserToDto() {

        UserResponseDto result = userConverter.createDtoFromUser(user);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(role, result.getRole());
        assertEquals("avatar1", result.getAvatarId());
        assertEquals(25, result.getAge());
    }

    @Test
    void createUserFromDto_ShouldConvertDtoToUser_WhenRoleExists() {

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));

        User result = userConverter.createUserFromDto(registrationRequest);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(role, result.getRole());
        assertEquals("avatar1", result.getAvatarId());
        assertEquals(25, result.getAge());

        verify(passwordEncoder).encode("Password!123");
        verify(roleRepository).findByRoleName("USER");
    }

    @Test
    void createUserFromDto_ShouldCreateAndSaveRole_WhenRoleDoesNotExist() {

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        User result = userConverter.createUserFromDto(registrationRequest);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(role, result.getRole());
        assertEquals("avatar1", result.getAvatarId());
        assertEquals(25, result.getAge());

        verify(passwordEncoder).encode("Password!123");
        verify(roleRepository).findByRoleName("USER");
        verify(roleRepository).save(any(Role.class));
    }
}