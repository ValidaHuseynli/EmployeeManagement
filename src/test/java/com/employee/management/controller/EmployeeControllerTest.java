package com.employee.management.controller;

import com.employee.management.model.PositionDto;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.service.impl.EmployeeServiceImpl;
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
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveEmployeeTest() throws Exception {
        int positionId = 1;
        int departmentId = 1;
        int employeeId = 1;
        PositionDto positionDto = PositionDto.builder().id(positionId).build();
        EmployeeRequest request = new EmployeeRequest("Sitara", "Amrehmedova", "@AStar", positionDto);
        EmployeeResponse response = new EmployeeResponse(employeeId, "Sitara", "Amrehmedova", "@AStar", positionDto);

        when(employeeService.saveEmployee(request)).thenReturn(Optional.of(response));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee_management/employees")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("Sitara"))
                .andExpect(jsonPath("$.surname").value("Amrehmedova"))
                .andExpect(jsonPath("$.email").value("@AStar"));
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        int positionId = 1;
        int employeeId = 1;
        PositionDto positionDto = PositionDto.builder().id(positionId).build();
        EmployeeResponse response = new EmployeeResponse(employeeId, "Sitara", "Amrehmedova", "@AStar", positionDto);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(response));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee_management/employees/{id}", employeeId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("Sitara"))
                .andExpect(jsonPath("$.surname").value("Amrehmedova"))
                .andExpect(jsonPath("$.email").value("@AStar"));
    }

    @Test
    void getAllEmployeesTest() throws Exception {
        int positionId1 = 12;
        int positionId2 = 13;
        int employeeId1 = 1;
        int employeeId2 = 21;
        PositionDto positionDto1 = PositionDto.builder().id(positionId1).build();
        PositionDto positionDto2 = PositionDto.builder().id(positionId2).build();
        EmployeeResponse response1 = new EmployeeResponse(employeeId1, "Sitara", "Amrehmedova", "@AStar", positionDto1);
        EmployeeResponse response2 = new EmployeeResponse(employeeId2, "Sebine", "Rustemli", "@RSabina", positionDto2);

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee_management/employees")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void updateEmployeeTest() throws Exception {
        int positionId = 1;
        int employeeId = 1;
        PositionDto positionDto = PositionDto.builder().id(positionId).build();
        EmployeeRequest request = new EmployeeRequest("Sabina", "Rustemli", "@SRustemli", positionDto);
        EmployeeResponse response = new EmployeeResponse(employeeId, "Sabina", "Rustemli", "@SRustemli", positionDto);

        when(employeeService.updateEmployee(employeeId, request)).thenReturn(Optional.of(response));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employee_management/employees/{id}", employeeId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteEmployeeTest() throws Exception {
        int employeeId = 1;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee_management/employees/{id}", employeeId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
