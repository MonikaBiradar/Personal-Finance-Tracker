package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.UserRequest;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody UserRequest request)
    {
        return userService.addUser(
                request.getUserName(),
                request.getPhoneNumber(),
                request.getEmail()
        );
    }

    @PatchMapping("/users/{userId}/deactivate")
    public User deactivateUser(@PathVariable Long userId)
    {
        return userService.deactivateUser(userId);
    }
}
