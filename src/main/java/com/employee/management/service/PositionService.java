package com.employee.management.service;

import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    Optional<PositionResponse> savePosition(PositionRequest request);

    Optional<PositionResponse> getPositionById(int id);

    List<PositionResponse> getAllPositions();

    Optional<PositionResponse> updatePosition(int id, PositionRequest request);

    void deletePosition(int id);

}