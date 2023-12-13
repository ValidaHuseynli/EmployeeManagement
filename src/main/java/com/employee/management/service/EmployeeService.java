package com.employee.management.service;

import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeResponse> saveEmployee(EmployeeRequest request);

    Optional<EmployeeResponse> getEmployeeById(int id);

    List<EmployeeResponse> getAllEmployees();

    Optional<EmployeeResponse> updateEmployee(int id, EmployeeRequest request);

    void deleteEmployee(int id);
}
