package by.it.academy.onlinestore.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserRequestDto {
    private Integer id;
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "Name is less than 2 symbols or contains non-characters")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "Name is less than 2 symbols or contains non-characters")
    private String lastName;
    @Pattern(regexp = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
            message = "Email invalid")
    private String email;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must have at least 8 symbols, 1 upper-case symbol, 1 digit and 1 special character")
    private String password;
    private String role;
}
