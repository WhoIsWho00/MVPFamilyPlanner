package com.example.familyplanner.service.converter;

import com.example.familyplanner.dto.requests.signInUp.RegistrationRequest;
import com.example.familyplanner.dto.responses.UserResponseDto;
import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.RoleRepository;
import com.example.familyplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserResponseDto createDtoFromUser(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getAvatarId(),
                user.getAge()
        );
    }

    public User createUserFromDto(RegistrationRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setAvatarId(request.getAvatarId());
        newUser.setAge(request.getAge());


        Optional<Role> defaultRole = roleRepository.findByRoleName("USER");

        if(defaultRole.isEmpty()) {
            Role userRole = new Role("USER");
            defaultRole = Optional.of(roleRepository.save(userRole));
        }

        newUser.setRole(defaultRole.get());
        return newUser;
    }
}