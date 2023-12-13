package com.employee.management.model;


public record EmployeeRequest(String name,
                              String surname,
                              String email,
                              PositionDto position_id ) {
}
