package com.employee.management.controller;

import com.employee.management.model.DepartmentDto;
import com.employee.management.model.PositionRequest;
import com.employee.management.model.PositionResponse;
import com.employee.management.service.impl.PositionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionServiceImpl positionService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void savePositionTest() throws Exception {
        int departmentId = 1;
        int positionId = 1;
        DepartmentDto departmentDto = DepartmentDto.builder().id(departmentId).build();
        PositionRequest request = new PositionRequest("Finance Manager", 1500, departmentDto);
        PositionResponse response = new PositionResponse(positionId, "Finance Manager", 1500, departmentDto);
        when(positionService.savePosition(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee_management/positions")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value("Finance Manager"));
    }

    @Test
    void getPositionByIdTest() throws Exception {
        int positionId = 1;
        int departmentId = 1;
        DepartmentDto departmentDto = DepartmentDto.builder().id(departmentId).build();
        PositionResponse response = new PositionResponse(positionId, "Finance Manager", 1500, departmentDto);

        when(positionService.getPositionById(positionId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee_management/positions/{id}", positionId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(positionId))
                .andExpect(jsonPath("$.name").value("Finance Manager"))
                .andExpect(jsonPath("$.department_id").value(departmentDto));
    }

    @Test
    void getAllPositionsTest() throws Exception {
        int positionId1 = 1;
        int positionId2 = 1;
        int departmentId1 = 1;
        int departmentId2 = 1;
        DepartmentDto departmentDto1 = DepartmentDto.builder().id(departmentId1).build();
        DepartmentDto departmentDto2 = DepartmentDto.builder().id(departmentId2).build();
        PositionResponse response1 = new PositionResponse(positionId1, "Finance Manager", 1500, departmentDto1);
        PositionResponse response2 = new PositionResponse(positionId2, "Sales Manager", 1500, departmentDto2);

        when(positionService.getAllPositions()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee_management/positions")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void updatePositionTest() throws Exception {
        int positionId = 1;
        int departmentId = 1;
        DepartmentDto departmentDto = DepartmentDto.builder().id(departmentId).build();
        PositionRequest request = new PositionRequest("Sales Manager", 1500, departmentDto);
        PositionResponse response = new PositionResponse(positionId, "Finance Manager", 1500, departmentDto);

        when(positionService.updatePosition(positionId, request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employee_management/positions/{id}", positionId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(positionId))
                .andExpect(jsonPath("$.name").value("Finance Manager"))
                .andExpect(jsonPath("$.department_id").value(departmentDto));
    }

    @Test
    void deletePositionTest() throws Exception {
        int positionId = 1;
        willDoNothing().given(positionService).deletePosition(positionId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee_management/positions/{id}", positionId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}