package com.employee.management.service;

import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse saveEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(int id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(int id, EmployeeRequest request);

    void deleteEmployee(int id);
}
