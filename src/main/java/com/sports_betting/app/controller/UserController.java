package com.sports_betting.app.controller;

import com.sports_betting.app.service.UserService;

import org.springframework.web.bind.annotation.RestController;

import com.sports_betting.app.model.user.User;
import com.sports_betting.app.model.user.UserLoginRequest;
import com.sports_betting.app.model.user.UserResponse;
import com.sports_betting.app.model.user.UserSignupRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody UserSignupRequest userSignupRequest) {
        User savedUser = userService.registerUser(userSignupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
        try {
            User user = userService.loginUser(request);
            UserResponse response = new UserResponse(user);
            return ResponseEntity.ok(response); // In a real app, you'd return a JWT Token here
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
