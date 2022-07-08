package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.UserDto;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.mappers.UserMapper;
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
    @PreAuthorize("hasAuthority('USER')")
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
       User user = userService.createUser(UserMapper.INSTANCE.convertToUser(userDto));
       return UserMapper.INSTANCE.convertToUserDto(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto findUserById(@PathVariable(value = "id") Integer userId) {
        User user = userService.findUserById(userId);
        return UserMapper.INSTANCE.convertToUserDto(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> showAllUsers(){
        List<User> users = userService.findAllUser();
        return users.stream()
                .map(UserMapper.INSTANCE::convertToUserDto)
                .collect(Collectors.toList());
    }
}
