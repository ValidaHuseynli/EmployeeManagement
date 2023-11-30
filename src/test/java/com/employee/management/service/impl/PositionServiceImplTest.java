package com.employee.management.service.impl;

import com.employee.management.entity.Department;
import com.employee.management.entity.Position;
import com.employee.management.exception.NotFoundException;
import com.employee.management.model.DepartmentDto;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.repository.PositionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@SpringBootTest
class PositionServiceImplTest {
    @Mock
    private PositionRepository positionRepository;
    @InjectMocks
    private PositionServiceImpl positionService;

    @Test
    void savePositionTest() {
        //given
        int positionId = 1;
        int departmentId = 1;
        DepartmentDto dto = DepartmentDto.builder().id(departmentId).build();
        PositionRequest request = new PositionRequest("Sales Manager", 1500.0, dto);
        Position position = Position.builder().id(positionId).name("Sales Manager").build();

        //when
        when(positionRepository.save(any(Position.class))).thenReturn(position);
        PositionResponse response = positionService.savePosition(request);

        //then
        assertThat(response).isNotNull();
    }

    @Test
    void getPositionByIdSuccessTest() {
        //given
        int positionId = 1;
        int departmentId = 1;
        Department department = Department.builder().id(departmentId).name("Finance").build();
        Position position = Position.builder().id(positionId).name("Sales Manager").salary(1500).department_id(department).build();
        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));

        //when
        PositionResponse response = positionService.getPositionById(positionId);

        //then
        assertThat(response).isNotNull();
        assertEquals(1, response.id());
        assertEquals("Sales Manager", response.name());
    }

    @Test
    void getPositionByIdErrorTest() {
        //given
        given(positionRepository.findById(anyInt())).willReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> positionService.getPositionById(anyInt()));
    }

    @Test
    void getAllPositionsTest() {
        //given
        int positionId1 = 1;
        int positionId2 = 1;
        int departmentId1 = 1;
        int departmentId2 = 1;
        Department department1 = Department.builder().id(departmentId1).name("Finance").build();
        Department department2 = Department.builder().id(departmentId2).name("Sales").build();
        Position position1 = Position.builder().id(positionId1).name("Sales Manager").salary(1500).department_id(department1).build();
        Position position2 = Position.builder().id(positionId2).name("Finance Manager").salary(1300).department_id(department2).build();
        //when
        when(positionRepository.findAll()).thenReturn(Arrays.asList(position1, position2));
        List<PositionResponse> responses = positionService.getAllPositions();
        //then
        assertThat(responses).isNotNull();
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    void updatePositionSuccessTest() {
        //given
        int positionId = 1;
        int departmentId = 1;
        DepartmentDto dto = DepartmentDto.builder().id(departmentId).build();
        PositionRequest request = new PositionRequest("Sales Manager", 1400, dto);
        Department department = Department.builder().id(departmentId).name("Finance").build();
        Position position = Position.builder().id(positionId).name("Finance Manager").salary(1500).department_id(department).build();

        //when
        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        when(positionRepository.save(any(Position.class))).thenReturn(position);
        PositionResponse response = positionService.updatePosition(positionId, request);

        //then
        assertNotNull(response);
        assertEquals("Sales Manager", response.name());
        assertEquals(1400, response.salary());
    }

    @Test
    void updatePositionErrorTest() {
        int positionId = 1;
        int departmentId = 1;
        DepartmentDto dto = DepartmentDto.builder().id(departmentId).build();
        PositionRequest request = new PositionRequest("Sales Manager", 1400, dto);
        when(positionRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> positionService.updatePosition(positionId, request));
    }


    @Test
    void deletePositionSuccessTest() {
        //given
        int positionId = 1;
        int departmentId = 1;
        Department department = Department.builder().id(departmentId).name("Finance").build();
        Position position = Position.builder().id(positionId).name("Finance Manager").salary(1500).department_id(department).build();

        //when
        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        willDoNothing().given(positionRepository).delete(position);
        positionService.deletePosition(positionId);

        //then
        verify(positionRepository, times(1)).findById(positionId);
        verify(positionRepository, times(1)).delete(position);
    }

    @Test
    void deletePositionErrorTest() {
        int positionId = 1;

        when(positionRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> positionService.deletePosition(positionId));

    }
}