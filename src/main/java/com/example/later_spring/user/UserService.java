package com.example.later_spring.user;



import com.example.later_spring.user.dto.UserDto;

import java.util.List;

interface UserService {
    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto user);
}