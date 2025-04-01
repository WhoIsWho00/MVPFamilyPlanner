package com.example.familyplanner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//Может использоваться фронтом для получения инфо о текущем пользователе
//Например, фронт после логина дергает /secured/user, получает имя и показывает "Привет, [username]!".
//@RestController
//@RequestMapping("/secured")
//public class MainController {
//    @GetMapping("/user")
//    public String userAccess(Principal principal) {
//        if (principal == null) {
//            return null;
//        }
//        return principal.getName();
//    }
//}
