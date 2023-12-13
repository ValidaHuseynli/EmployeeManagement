package com.employee.management.controller;

import com.employee.management.model.LoginRequest;
import com.employee.management.model.LoginResponse;
import com.employee.management.model.UserRequest;
import com.employee.management.model.UserResponse;
import com.employee.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("${root.url}/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public UserResponse saveUser(@RequestBody @Valid UserRequest request) {
        return userService.saveUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }


}
