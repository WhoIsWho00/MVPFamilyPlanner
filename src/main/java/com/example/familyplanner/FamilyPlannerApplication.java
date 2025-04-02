package com.example.familyplanner;

import com.example.familyplanner.Security.JWT.JwtCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.example.familyplanner.repository")
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
