package com.employee.management.mapper;

import com.employee.management.entity.User;
import com.employee.management.model.UserRequest;
import com.employee.management.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "status", expression = "java(getStatus())")
    public abstract User modelToEntity(UserRequest request);

    public abstract UserResponse entityToModel(User user);


    @Named("getStatus")
    protected boolean getStatus() {
        return true;
    }
}
