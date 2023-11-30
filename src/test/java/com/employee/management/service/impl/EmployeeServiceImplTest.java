package com.employee.management.service.impl;

import com.employee.management.entity.Department;
import com.employee.management.entity.Employee;
import com.employee.management.entity.Position;
import com.employee.management.exception.NotFoundException;
import com.employee.management.model.DepartmentDto;
import com.employee.management.model.EmployeeRequest;
import com.employee.management.model.EmployeeResponse;
import com.employee.management.model.PositionDto;
import com.employee.management.repository.EmployeeRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void saveEmployeeTest() {
        //given
        int employeeId = 1;
        int departmentId = 1;
        int positionId = 1;
        DepartmentDto departmentDto = DepartmentDto.builder().id(departmentId).build();
        PositionDto positionDto = PositionDto.builder().id(positionId).build();
        EmployeeRequest request = new EmployeeRequest
                ("Aygul", "Huseynli", "@Ahuseyn", departmentDto, positionDto);

        Department department = Department.builder().id(departmentId).name("Finance").build();

        Position position = Position.builder()
                .id(positionId).name("Finance Manager").salary(1500).department_id(department).build();
        Employee employee = Employee.builder()
                .id(employeeId).name("Aygul").surname("Huseynli").email("@AHuseynli").department_id(department).position_id(position).build();

        //when
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeResponse response = employeeService.saveEmployee(request);

        //then
        assertNotNull(response);
        assertEquals(1, response.id());
    }

    @Test
    void getEmployeeByIdSuccessTest() {
        //given
        int employeeId = 1;
        int departmentId = 1;
        int positionId = 1;
        Department department = Department.builder().id(departmentId).name("Finance").build();

        Position position = Position.builder()
                .id(positionId).name("Finance Manager").salary(1500).department_id(department).build();
        Employee employee = Employee.builder()
                .id(employeeId).name("Aygul").surname("Huseynli").email("@AHuseynli").department_id(department).position_id(position).build();

        //when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        EmployeeResponse response = employeeService.getEmployeeById(employeeId);
        //then
        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Aygul", response.name());
    }

    @Test
    void getEmployeeByIdErrorTest() {
        int employeeId = 1;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    @Test
    void getAllEmployeesTest() {
        //given
        int employeeId1 = 121;
        int employeeId2 = 12;
        int departmentId1 = 13;
        int departmentId2 = 14;
        int positionId1 = 15;
        int positionId2 = 16;
        Department department1 = Department.builder().id(departmentId1).name("Finance").build();
        Department department2 = Department.builder().id(departmentId2).name("Sale").build();

        Position position1 = Position.builder()
                .id(positionId1).name("Finance Manager").salary(1500).department_id(department1).build();
        Position position2 = Position.builder()
                .id(positionId2).name("Sale Manager").salary(1500).department_id(department2).build();
        Employee employee1 = Employee.builder()
                .id(employeeId1).name("Aygul").surname("Huseynli").email("@AHuseynli").department_id(department1).position_id(position1).build();
        Employee employee2 = Employee.builder()
                .id(employeeId2).name("Sevinc").surname("Allahyarova").email("@ASevinc").department_id(department2).position_id(position2).build();


        //when
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        //then
        assertThat(responses).isNotNull();
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    void updateEmployeeSuccessTest() {
        //given
        int employeeId = 23;
        int departmentId = 41;
        int positionId = 15;
        DepartmentDto departmentDto = DepartmentDto.builder().id(departmentId).build();
        PositionDto positionDto = PositionDto.builder().id(positionId).build();
        EmployeeRequest request = new EmployeeRequest
                ("Aygul", "Huseynli", "@Ahuseyn", departmentDto, positionDto);

        Department department = Department.builder().id(departmentId).name("Finance").build();
        Position position = Position.builder()
                .id(positionId).name("Finance Manager").salary(1500).department_id(department).build();
        Employee employee = Employee.builder()
                .id(employeeId).name("Aygul").surname("Huseynli").email("@AHuseyn").department_id(department).position_id(position).build();

        //when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeResponse response = employeeService.updateEmployee(employeeId, request);
        //then
        assertNotNull(response);
        assertEquals("Aygul", response.name());
        assertEquals("Huseynli", response.surname());
        assertEquals("@Ahuseyn", response.email());
    }

    @Test
    void updateEmployeeErrorTest() {
        int employeeId = 1;
        int departmentId = 41;
        int positionId = 15;
        DepartmentDto departmentDto = DepartmentDto.builder().id(departmentId).build();
        PositionDto positionDto = PositionDto.builder().id(positionId).build();
        EmployeeRequest request = new EmployeeRequest
                ("Aygul", "Huseynli", "@Ahuseyn", departmentDto, positionDto);

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.updateEmployee(employeeId, request));
    }

    @Test
    void deleteEmployeeSuccessTest() {
        //given
        int employeeId = 1;
        int departmentId = 1;
        int positionId = 1;
        Department department = Department.builder().id(departmentId).name("Finance").build();

        Position position = Position.builder()
                .id(positionId).name("Finance Manager").salary(1500).department_id(department).build();
        Employee employee = Employee.builder()
                .id(employeeId).name("Aygul").surname("Huseynli").email("@AHuseynli").department_id(department).position_id(position).build();

        //when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        employeeService.deleteEmployee(employeeId);

        //then
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void deleteEmployeeErrorTest() {
        int employeeId = 1;

        //when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> employeeService.deleteEmployee(employeeId));
    }
}