package com.rayan.demo.model.mapper;


import com.rayan.demo.model.domain.User;
import com.rayan.demo.model.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserRegisterResDto(User user);
}