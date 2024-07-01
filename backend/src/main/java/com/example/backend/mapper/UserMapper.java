package com.example.backend.mapper;

import com.example.backend.dto.UserDto;
import com.example.backend.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoUser(UserDto userDto);

    UserDto userToDto(User result);
//  static User userDtoUser(UserDto userDto){
//      return new User(userDto.getId(),userDto.getUsername(),userDto.getPassword());
//  }
//
//     static UserDto userToDto(User user){
//      return new UserDto(user.getId(), user.getUsername(), user.getPassword());
//     }
}
