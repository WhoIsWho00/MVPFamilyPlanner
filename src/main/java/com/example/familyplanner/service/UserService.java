package com.example.familyplanner.service;

import com.example.familyplanner.Security.JWT.UserDetailImpl;
import com.example.familyplanner.entity.User;
import com.example.familyplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
//Главна задача этого сервиса состоит в том, чтобы находить пользователя по email при попытке аутентификации.
//В случае успеха - возвращает преобразованного пользователя в понятном формате для Spring Security
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", email)
                ));

        return UserDetailImpl.build(user);
    }

    public void deleteUserById(UUID id){
        if(id == null){
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}