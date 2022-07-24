package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.user.UserRequestDto;
import by.it.academy.onlinestore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for user
 */
@Mapper
public interface UserRequestMapper {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    /**
     * Converts entity to Dto
     * @param user to convert
     * @return UserRequestDto
     */
    UserRequestDto convertToUserDto(User user);

    /**
     * Converts Dto to entity
     * @param userSaveDto Dto to convert
     * @return user entity
     */
    User convertToUser(UserRequestDto userSaveDto);
}
