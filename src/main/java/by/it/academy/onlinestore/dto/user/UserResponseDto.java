package by.it.academy.onlinestore.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
