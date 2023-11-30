package com.employee.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.service.impl.DepartmentServiceImpl;
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
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentServiceImpl departmentService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveDepartmentTest() throws Exception {
        DepartmentRequest request = new DepartmentRequest("Finance");
        DepartmentResponse response = new DepartmentResponse(73, "Finance");

        when(departmentService.saveDepartment(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employee_management/departments")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(73))
                .andExpect(jsonPath("$.name").value("Finance"));

    }

    @Test
    void getDepartmentByIdTest() throws Exception {
        int departmentId = 73;
        DepartmentResponse response = new DepartmentResponse(departmentId, "Finance");
        when(departmentService.getDepartmentById(departmentId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee_management/departments/{id}", departmentId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value("Finance"));
    }

    @Test
    void getAllDepartmentsTest() throws Exception {
        int departmentId1 = 73;
        int departmentId2 = 732;
        DepartmentResponse response1 = new DepartmentResponse(departmentId1, "Finance");
        DepartmentResponse response2 = new DepartmentResponse(departmentId2, "Sale");
        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee_management/departments")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void updateDepartmentTest() throws Exception {
        int departmentId = 73;
        DepartmentRequest request = new DepartmentRequest("Sale");
        DepartmentResponse response = new DepartmentResponse(departmentId, "Finance");
        when(departmentService.updateDepartment(departmentId, request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employee_management/departments/{id}", departmentId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value("Finance"));
    }

    @Test
    void deleteDepartmentTest() throws Exception {
        int departmentId = 73;
        willDoNothing().given(departmentService).deleteDepartment(departmentId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee_management/departments/{id}", departmentId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}