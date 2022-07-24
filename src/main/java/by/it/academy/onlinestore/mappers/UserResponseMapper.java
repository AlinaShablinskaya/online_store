package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.user.UserResponseDto;
import by.it.academy.onlinestore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for user
 */
@Mapper
public interface UserResponseMapper {
    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    /**
     * Converts entity to Dto
     * @param user to convert
     * @return UserResponseDto
     */
    UserResponseDto convertToUserDto(User user);

    /**
     * Converts Dto to entity
     * @param userSaveDto Dto to convert
     * @return user entity
     */
    User convertToUser(UserResponseDto userSaveDto);
}
