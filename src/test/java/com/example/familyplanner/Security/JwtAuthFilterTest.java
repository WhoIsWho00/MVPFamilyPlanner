package com.example.familyplanner.Security;

import com.example.familyplanner.Security.JWT.JwtCore;
import com.example.familyplanner.Security.JWT.UserDetailImpl;
import com.example.familyplanner.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthFilterTest {

    @Mock
    private JwtCore jwtCore;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetailImpl userDetails;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_ShouldAuthenticateUser_WhenTokenIsValid() throws ServletException, IOException {

        when(request.getHeader("Authorization")).thenReturn("Bearer valid_token");
        when(jwtCore.validateToken("valid_token")).thenReturn(true);
        when(jwtCore.getUserNameFromJwt("valid_token")).thenReturn("test@example.com");
        when(userService.loadUserByUsername("test@example.com")).thenReturn(userDetails);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateUser_WhenNoToken() throws ServletException, IOException {

        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateUser_WhenInvalidToken() throws ServletException, IOException {

        when(request.getHeader("Authorization")).thenReturn("Bearer invalid_token");

        when(jwtCore.validateToken("invalid_token")).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateUser_WhenInvalidAuthHeader() throws ServletException, IOException {

        when(request.getHeader("Authorization")).thenReturn("invalid_header_format");

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}