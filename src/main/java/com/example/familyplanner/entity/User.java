package com.example.familyplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false, unique = true)

    private String email;


    @Column (nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn (name = "role")
    private Role role;

}