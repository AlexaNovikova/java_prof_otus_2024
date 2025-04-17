package ru.otus.crm.mapper;

import ru.otus.crm.dto.UserDto;
import ru.otus.crm.model.User;

public class UserMapper {
    public UserDto modelToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
