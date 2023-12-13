package com.employee.management.service;

import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Optional<DepartmentResponse> saveDepartment(DepartmentRequest request);

    Optional<DepartmentResponse> getDepartmentById(int id);

    List<DepartmentResponse> getAllDepartments();

    Optional<DepartmentResponse> update(int id, DepartmentRequest request);

    void deleteDepartment(int id);

}
