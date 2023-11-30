package com.employee.management.mapper;

import com.employee.management.entity.Employee;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class EmployeeMapper {
    public static final EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "status", expression = "java(getStatus())")
    public abstract Employee modelToEntity(EmployeeRequest request);

    public abstract EmployeeResponse entityToModel(Employee employee);

    public abstract List<EmployeeResponse> entitiesToModel(List<Employee> employees);

    public abstract void modelToEntityUpdate(@MappingTarget Employee employee, EmployeeRequest request);

    @Named("getStatus")
    protected boolean getStatus() {
        return true;
    }
}
