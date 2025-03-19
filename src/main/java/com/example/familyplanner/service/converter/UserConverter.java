package com.example.familyplanner.service.converter;

import com.example.familyplanner.dto.RegistrationRequest;
import com.example.familyplanner.dto.UserResponseDto;
import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.RoleRepository;
import com.example.familyplanner.repository.UserRepository;
import com.example.familyplanner.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public UserResponseDto createDtoFromUser(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User createUserFromDto(RegistrationRequest request) {
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(hashedPassword);

        Optional<Role> defaultRole = roleRepository.findByRoleName("USER");

        if(defaultRole.isPresent())
        {
            newUser.setRole(defaultRole.get());
            return newUser;
        }
        else throw (new NotFoundException("Role not found"));

    }

}
