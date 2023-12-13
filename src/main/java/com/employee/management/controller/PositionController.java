package com.employee.management.controller;

import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${root.url}/positions")
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public Optional<PositionResponse> savePosition(@RequestBody PositionRequest request) {
        return positionService.savePosition(request);
    }

    @GetMapping("/{id}")
    public Optional<PositionResponse> getPositionById(@PathVariable int id) {
        return positionService.getPositionById(id);
    }

    @GetMapping
    public List<PositionResponse> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PutMapping("/{id}")
    public Optional<PositionResponse> updatePosition(@PathVariable int id, @RequestBody PositionRequest request) {
        return positionService.updatePosition(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable int id) {
        positionService.deletePosition(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}