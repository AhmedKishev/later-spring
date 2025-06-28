package com.example.later_spring.user.mapper;


import com.example.later_spring.user.User;
import com.example.later_spring.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
