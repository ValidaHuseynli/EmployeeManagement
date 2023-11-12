package com.employee.management.mapper;

import com.employee.management.entity.Position;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PositionMapper {
    public static final PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    public abstract Position modelToEntity(PositionRequest request);
    public abstract PositionResponse entityToModel(Position position);

    public abstract List<PositionResponse> entitiesToModel(List<Position> positions);

    public abstract void modelToEntityUpdate (@MappingTarget Position position, PositionRequest request);



}
