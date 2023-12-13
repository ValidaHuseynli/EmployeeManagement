package com.employee.management.controller;

import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${root.url}/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public Optional<DepartmentResponse> saveDepartment(@RequestBody DepartmentRequest request) {
        return departmentService.saveDepartment(request);
    }

    @GetMapping("/{id}")
    public Optional<DepartmentResponse> getDepartmentById(@PathVariable int id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PutMapping("/{id}")
    public Optional<DepartmentResponse> updateDepartment(@PathVariable int id, @RequestBody DepartmentRequest request) {
        return departmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
