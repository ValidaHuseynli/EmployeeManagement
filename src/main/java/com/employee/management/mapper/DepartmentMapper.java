package com.employee.management.mapper;

import com.employee.management.entity.Department;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DepartmentMapper {
    public static final DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    public abstract Department modelToEntity(DepartmentRequest request);

    public abstract DepartmentResponse entityToModel(Department department);

    public abstract List<DepartmentResponse> entitiesToModel(List<Department> departments);

    public abstract void modelToEntityUpdate(@MappingTarget Department department, DepartmentRequest request);

}
