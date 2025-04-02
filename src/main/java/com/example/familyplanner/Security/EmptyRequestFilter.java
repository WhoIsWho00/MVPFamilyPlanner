package com.example.familyplanner.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmptyRequestFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Перевіряємо, чи це POST запит на /api/auth/sign-up
        if (request.getMethod().equals("POST") && request.getRequestURI().endsWith("/api/auth/sign-up")) {
            // Перевіряємо, чи тіло запиту порожнє
            if (request.getContentLength() <= 0) {
                // Повертаємо 400 Bad Request
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("timestamp", new Date());
                errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
                errorResponse.put("error", "Bad Request");
                errorResponse.put("message", "Request body is missing or malformed");
                errorResponse.put("path", request.getRequestURI());

                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return; // Завершуємо обробку запиту
            }
        }

        // Якщо все в порядку, продовжуємо ланцюг фільтрів
        filterChain.doFilter(request, response);
    }
}