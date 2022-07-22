package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.UserRequestDto;
import by.it.academy.onlinestore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRequestMapper {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    UserRequestDto convertToUserDto(User user);

    User convertToUser(UserRequestDto userSaveDto);
}
