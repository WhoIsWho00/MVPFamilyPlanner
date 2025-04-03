package com.example.familyplanner.service.converter;

import com.example.familyplanner.dto.requests.signInUp.RegistrationRequest;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.RoleRepository;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserConverterTest {

    private UserConverter userConverter;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        roleRepository = mock(RoleRepository.class);
        userRepository = mock(UserRepository.class);
        userConverter = new UserConverter(passwordEncoder, roleRepository, userRepository);
    }

    @Test
    void createDtoFromUser_ShouldReturnUserResponseDto() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setRole(new Role("USER"));
        user.setAvatarId("avatar123");
        user.setAge(25);

        UserResponseDto dto = userConverter.createDtoFromUser(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getRole(), dto.getRole());
        assertEquals(user.getAvatarId(), dto.getAvatarId());
        assertEquals(user.getAge(), dto.getAge());
    }

    @Test
    void createUserFromDto_ShouldReturnUser() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setAvatarId("avatar123");
        request.setAge(25);

        Role defaultRole = new Role("USER");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        User user = userConverter.createUserFromDto(request);

        assertNotNull(user);
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(request.getAvatarId(), user.getAvatarId());
        assertEquals(request.getAge(), user.getAge());
        assertEquals(defaultRole, user.getRole());
    }

    @Test
    void createUserFromDto_ShouldCreateAndSaveDefaultRoleIfNotFound() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setAvatarId("avatar123");
        request.setAge(25);

        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        User user = userConverter.createUserFromDto(request);

        assertNotNull(user);
        assertEquals("USER", user.getRole().getRoleName());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void shouldThrowExpiredTokenException() {
        ExpiredTokenException exception = assertThrows(
            ExpiredTokenException.class,
            () -> { throw new ExpiredTokenException("Token has expired"); }
        );
        assertEquals("Token has expired", exception.getMessage());
    }

    @Test
    void shouldThrowExcessRegistrationLimitException() {
        ExcessRegistrationLimitException exception = assertThrows(
            ExcessRegistrationLimitException.class,
            () -> { throw new ExcessRegistrationLimitException("Registration limit exceeded"); }
        );
        assertEquals("Registration limit exceeded", exception.getMessage());
    }

    @Test
    void shouldThrowAccessDeniedException() {
        AccessDeniedException exception = assertThrows(
            AccessDeniedException.class,
            () -> { throw new AccessDeniedException("Access denied"); }
        );
        assertEquals("Access denied", exception.getMessage());
    }

    @Test
    void shouldThrowAlreadyExistException() {
        AlreadyExistException exception = assertThrows(
            AlreadyExistException.class,
            () -> { throw new AlreadyExistException("Entity already exists"); }
        );
        assertEquals("Entity already exists", exception.getMessage());
    }
}