package com.example.later_spring.user;


import com.example.later_spring.enums.UserState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name")
    String lastName;

    String email;

    @Column(name = "registration_date")
    Instant registrationDate = Instant.now();

    @Enumerated(EnumType.STRING)
    UserState state;
}