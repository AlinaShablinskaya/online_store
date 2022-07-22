package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.UserRequestDto;
import by.it.academy.onlinestore.dto.UserResponseDto;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.mappers.UserRequestMapper;
import by.it.academy.onlinestore.mappers.UserResponseMapper;
import by.it.academy.onlinestore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public UserRequestDto saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
       User user = userService.createUser(UserRequestMapper.INSTANCE.convertToUser(userRequestDto));
       return UserRequestMapper.INSTANCE.convertToUserDto(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto findUserById(@PathVariable(value = "id") Integer userId) {
        User user = userService.findUserById(userId);
        return UserResponseMapper.INSTANCE.convertToUserDto(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponseDto> showAllUsers(){
        List<User> users = userService.findAllUser();
        return users.stream()
                .map(UserResponseMapper.INSTANCE::convertToUserDto)
                .collect(Collectors.toList());
    }
}
