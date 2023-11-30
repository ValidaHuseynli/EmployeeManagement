package com.employee.management.service.impl;

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

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public DepartmentResponse saveDepartment(DepartmentRequest request) {
        logger.info("ActionLog.saveDepartment.start: {}", request);
        var department = DepartmentMapper.INSTANCE.modelToEntity(request);

        var saved = departmentRepository.save(department);

        var response = DepartmentMapper.INSTANCE.entityToModel(saved);
        logger.info("ActionLog.saveDepartment.end: {}", response);
        return response;
    }

    @Override
    public DepartmentResponse getDepartmentById(int id) {
        logger.info("ActionLog.getDepartment.start id: {}", id);

        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        var response = DepartmentMapper.INSTANCE.entityToModel(department);

        logger.info("ActionLog.getDepartment.end id: {}", id);
        return response;
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
    public DepartmentResponse updateDepartment(int id, DepartmentRequest request) {
        logger.info("ActionLog.updateDepartment.start id: {}", id);

        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department is not found for id: " + id));
        DepartmentMapper.INSTANCE.modelToEntityUpdate(department, request);
        var saved = departmentRepository.save(department);
        var response = DepartmentMapper.INSTANCE.entityToModel(saved);

        logger.info("ActionLog.updateDepartment.end id: {}", id);
        return response;
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
