package com.example.familyplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication; // Добавлено

import com.example.familyplanner.Security.JWT.JwtCore;

@SpringBootApplication
@EnableAutoConfiguration // Добавлено для включения Actuator
public class FamilyPlannerApplication {


	private JwtCore jwtCore;

	@Autowired
	public void setJwtCore(JwtCore jwtCore) {
		this.jwtCore = jwtCore;
	}

	public static void main(String[] args) {
		SpringApplication.run(FamilyPlannerApplication.class, args);
	}

}
