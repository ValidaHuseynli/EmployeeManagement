package com.employee.management.service.impl;

import com.employee.management.entity.Department;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.DepartmentMapper;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public Optional<DepartmentResponse> saveDepartment(DepartmentRequest request) {
        logger.info("ActionLog.saveDepartment.start: {}", request);
        var department = DepartmentMapper.INSTANCE.modelToEntity(request);

        var saved = departmentRepository.save(department);

        var response = DepartmentMapper.INSTANCE.entityToModel(saved);
        logger.info("ActionLog.saveDepartment.end: {}", response);
        return Optional.of(response);
    }

    @Override
    public Optional<DepartmentResponse> getDepartmentById(int id) {
        logger.info("ActionLog.getDepartment.start id: {}", id);

        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        var response = DepartmentMapper.INSTANCE.entityToModel(department);

        logger.info("ActionLog.getDepartment.end id: {}", id);
        return Optional.of(response);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        logger.info("ActionLog.getAllDepartments.start");

        var all = departmentRepository.findAll();
        var responses = DepartmentMapper.INSTANCE.entitiesToModel(all);

        logger.info("ActionLog.getAllDepartments.end");
        return responses;
    }

    @Override
    public Optional<DepartmentResponse> update(int id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .map(e ->
                {
                    e.setName(request.name());
                    return departmentRepository.save(e);
                }).orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        DepartmentResponse response = DepartmentMapper.INSTANCE.entityToModel(department);
        return Optional.of(response);
    }

    @Override
    public void deleteDepartment(int id) {
        logger.info("ActionLog.deleteDepartment.start id: {}", id);

        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        departmentRepository.delete(department);

        logger.info("ActionLog.deleteDepartment.end id: {}", id);
    }

}
