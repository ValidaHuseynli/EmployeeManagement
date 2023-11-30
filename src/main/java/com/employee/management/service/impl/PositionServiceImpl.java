package com.employee.management.service.impl;

import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.PositionMapper;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.repository.PositionRepository;
import com.employee.management.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Override
    public PositionResponse savePosition(PositionRequest request) {
        logger.info("ActionLog.savePosition.start: {}", request);

        var position = PositionMapper.INSTANCE.modelToEntity(request);
        var saved = positionRepository.save(position);
        var response = PositionMapper.INSTANCE.entityToModel(saved);

        logger.info("ActionLog.savePosition.end: {}", request);
        return response;
    }

    @Override
    public PositionResponse getPositionById(int id) {
        logger.info("ActionLog.getPosition.start id: {}", id);

        var position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position is not found for id: " + id));
        var response = PositionMapper.INSTANCE.entityToModel(position);

        logger.info("ActionLog.getPosition.end id: {}", id);
        return response;
    }

    @Override
    public List<PositionResponse> getAllPositions() {
        logger.info("ActionLog.getAllPositions.start");

        var all = positionRepository.findAll();
        var responses = PositionMapper.INSTANCE.entitiesToModel(all);

        logger.info("ActionLog.getAllPositions.end");
        return responses;
    }

    @Override
    public PositionResponse updatePosition(int id, PositionRequest request) {
        logger.info("ActionLog.updatePosition.start id: {}", id);

        var position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position is not found for id: " + id));
        PositionMapper.INSTANCE.modelToEntityUpdate(position, request);
        var saved = positionRepository.save(position);
        var response = PositionMapper.INSTANCE.entityToModel(saved);

        logger.info("ActionLog.updatePosition.end id: {}", id);
        return response;
    }

    @Override
    public void deletePosition(int id) {
        logger.info("ActionLog.deletePosition.start id: {}", id);

        var position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position is not found for id: " + id));
        positionRepository.delete(position);

        logger.info("ActionLog.deletePosition.end id: {}", id);
    }
}