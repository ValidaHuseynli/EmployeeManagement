package com.employee.management.model;


public record EmployeeRequest(String name, String surname,String email,DepartmentDto department_id,PositionDto position_id ) {
}
