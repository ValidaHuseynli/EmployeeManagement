package com.employee.management.service.impl;

import com.employee.management.entity.Employee;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.EmployeeMapper;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest request) {
        logger.info("ActionLog.saveEmployee.start: {}", request);
        Employee employee = EmployeeMapper.INSTANCE.modelToEntity(request);
        Employee saved = employeeRepository.save(employee);
        EmployeeResponse response = EmployeeMapper.INSTANCE.entityToModel(saved);

        logger.info("ActionLog.saveEmployee.end: {}", request);
        return response;
    }

    @Override
    public EmployeeResponse getEmployeeById(int id) {
        logger.info("ActionLog.getEmployeeById.start id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        EmployeeResponse response = EmployeeMapper.INSTANCE.entityToModel(employee);

        logger.info("ActionLog.getEmployeeById.end id: {}", id);
        return response;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        logger.info("ActionLog.getAllEmployees.start");

        List<Employee> all = employeeRepository.findAll();
        List<EmployeeResponse> responses = EmployeeMapper.INSTANCE.entitiesToModel(all);

        logger.info("ActionLog.getAllEmployees.start");
        return responses;
    }

    @Override
    public EmployeeResponse updateEmployee(int id, EmployeeRequest request) {
        logger.info("ActionLog.updateEmployee.start id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        EmployeeMapper.INSTANCE.modelToEntityUpdate(employee, request);
        Employee saved = employeeRepository.save(employee);
        EmployeeResponse response = EmployeeMapper.INSTANCE.entityToModel(saved);

        logger.info("ActionLog.updateEmployee.end id: {}", id);
        return response;
    }

    @Override
    public void deleteEmployee(int id) {
        logger.info("ActionLog.deleteEmployee.start id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        employeeRepository.delete(employee);

        logger.info("ActionLog.deleteEmployee.end id: {}", id);
    }
}
