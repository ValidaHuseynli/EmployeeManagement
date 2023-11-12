package com.employee.management.service;

import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse saveDepartment(DepartmentRequest request);
    DepartmentResponse getDepartment(int id);
    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(int id, DepartmentRequest request);

    void deleteDepartment(int id);

}
