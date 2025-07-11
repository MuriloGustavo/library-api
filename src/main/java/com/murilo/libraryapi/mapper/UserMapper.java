package com.murilo.libraryapi.mapper;

import com.murilo.libraryapi.dto.UserDTO;
import com.murilo.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
}
