package com.employee.management.service;

import com.employee.management.model.LoginRequest;
import com.employee.management.model.LoginResponse;
import com.employee.management.model.UserRequest;
import com.employee.management.model.UserResponse;

public interface UserService {
    UserResponse saveUser(UserRequest request);

    LoginResponse login(LoginRequest request);
}
