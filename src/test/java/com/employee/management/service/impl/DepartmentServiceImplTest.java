package com.employee.management.service.impl;

import com.employee.management.entity.Department;
import com.employee.management.exception.NotFoundException;
import com.employee.management.model.DepartmentRequest;
import com.employee.management.model.DepartmentResponse;
import com.employee.management.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void getDepartmentByIdSuccessTest() {
        //given
        int id = 1;
        Department department = Department.builder().id(id).name("Finance").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));

        //when
        Optional<DepartmentResponse> response = departmentService.getDepartmentById(id);
        DepartmentResponse response1 = response.get();

        //then
        assertThat(response).isNotNull();
        assertEquals(1, response1.id());
        assertEquals("Finance", response1.name());
    }

    @Test
    void getDepartmentByIdErrorTest() {
        given(departmentRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> departmentService.getDepartmentById(anyInt()));
    }

    @Test
    void saveDepartmentTest() {
        //given
        int id = 1;
        DepartmentRequest request = new DepartmentRequest("Fiance");
        Department department = Department.builder().id(id).name("Finance").build();
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        //when

        Optional<DepartmentResponse> response = departmentService.saveDepartment(request);
        //then
        assertThat(response).isNotNull();
    }

    @Test
    void getAllDepartmentsTest() {
        //given
        int id1 = 1;
        int id2 = 2;
        Department department1 = Department.builder().id(id1).name("Finance").build();
        Department department2 = Department.builder().id(id2).name("Sale").build();
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));

        //when
        List<DepartmentResponse> responses = departmentService.getAllDepartments();

        //then
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    void updateDepartmentSuccessTest() {
        //given
        int id = 1;
        DepartmentRequest request = new DepartmentRequest("Sale");
        Department department = Department.builder().id(id).name("Finance").build();
        given(departmentRepository.findById(id)).willReturn(Optional.of(department));
        department.setName(request.name());
        //when
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        Optional<DepartmentResponse> response = departmentService.update(id, request);

        //then
        assertNotNull(response);
    }

    @Test
    void updateDepartmentErrorTest() {
        int id = 1;
        DepartmentRequest request = new DepartmentRequest("Sale");
        given(departmentRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> departmentService.update(id, request));

    }

    @Test
    void deleteDepartmentSuccessTest() {
        //given
        int id = 1;
        Department department = Department.builder().id(id).name("Finance").build();
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        willDoNothing().given(departmentRepository).delete(department);

        //when
        departmentService.deleteDepartment(id);
        //then
        verify(departmentRepository, times(1)).findById(id);
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    void deleteDepartmentErrorTest() {
        //given
        given(departmentRepository.findById(anyInt())).willReturn(Optional.empty());

        //then & when
        assertThrows(NotFoundException.class, () -> departmentService.deleteDepartment(anyInt()));
    }

}
