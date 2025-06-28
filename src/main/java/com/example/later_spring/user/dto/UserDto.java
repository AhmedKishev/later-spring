package com.example.later_spring.user.dto;


import com.example.later_spring.enums.UserState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String registrationDate;
    UserState state;
}
