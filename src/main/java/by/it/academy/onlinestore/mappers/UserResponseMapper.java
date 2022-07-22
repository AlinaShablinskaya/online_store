package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.UserResponseDto;
import by.it.academy.onlinestore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserResponseMapper {
    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    UserResponseDto convertToUserDto(User user);

    User convertToUser(UserResponseDto userSaveDto);
}
