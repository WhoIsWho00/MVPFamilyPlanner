package com.example.familyplanner.service.validation;

import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidationService {

    private final UserRepository userRepository;

    public boolean userExists(String email) {
        Optional<User> existUser = userRepository.findByEmail(email);
        return existUser.isPresent();
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

}
