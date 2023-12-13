package com.employee.management.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "Name is mandatory")
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @NotNull(message = "Surname is mandatory")
    @NotEmpty(message = "surname can not be empty")
    private String surname;

    @Email(message = "invalid email address")
    @NotNull(message = "email is mandatory")
    @NotEmpty(message = "email can not be empty")
    private String email;

    @NotNull(message = "username is mandatory")
    @NotEmpty(message = "username can not be empty")
    @Size(min = 5, message = "Username must be at least 6 characters long")
    private String username;

    @NotNull( message = "password is mandatory")
    @NotEmpty(message = "password can not be empty")
    @Size(min = 5, max = 100, message = "password must be between 6 & 100")
    private String password;

    @NotNull(message = "role is mandatory")
    private Set<RoleDto> role;
}
