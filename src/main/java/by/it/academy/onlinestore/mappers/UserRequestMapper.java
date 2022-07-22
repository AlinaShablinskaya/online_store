package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.UserDto;
import by.it.academy.onlinestore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto convertToUserDto(User user);

    User convertToUser(UserDto userDto);
}
