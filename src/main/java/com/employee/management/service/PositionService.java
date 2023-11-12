package com.employee.management.service;

import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;

import java.util.List;

public interface PositionService {

    PositionResponse savePosition(PositionRequest request);

    PositionResponse getPositionById(int id);

    List<PositionResponse> getAllPositions();
    PositionResponse updatePosition(int id, PositionRequest request);
    void deletePosition(int id);

}