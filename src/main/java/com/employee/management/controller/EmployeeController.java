package com.employee.management.controller;

import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${root.url}/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public Optional<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.saveEmployee(request);
    }

    @GetMapping("/{id}")
    public Optional<EmployeeResponse> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Optional<EmployeeResponse> updateEmployee(@PathVariable int id, EmployeeRequest request) {
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
