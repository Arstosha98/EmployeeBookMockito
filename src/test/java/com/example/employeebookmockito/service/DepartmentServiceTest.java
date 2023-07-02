package com.example.employeebookmockito.service;

import com.example.employeebookmockito.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    List<Employee> employees = Arrays.asList(
            new Employee("ivan", "ivanov", 1, 10_000),
            new Employee("marina", "ivanova", 1, 20_000),
            new Employee("oleg", "ivanov", 2, 15_000),
            new Employee("sergey", "ivanov", 2, 25_000),
            new Employee("olga", "ivanova", 3, 50_000)
    );

    @BeforeEach
    void setup(){
        when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    void sumSalary(){
        double actual = departmentService.getEmployeeSalarySum(2);
        assertEquals(40_000,actual, 0.000001);
    }

    @Test
    void maxSalary(){
        double actual = departmentService.getEmployeeMaxSalary(1);
        assertEquals(20_000,actual);
    }

    @Test
    void minSalary (){
        double actual = departmentService.getEmployeeMinSalary(1);
        assertEquals(10_000,actual);
    }

    @Test
    void getAllByDepartment(){
        List<Employee> actual = departmentService.getEmployeeByDepartment(2);
        List<Employee> expected = Arrays.asList(
                new Employee("oleg", "ivanov", 2, 15_000),
                new Employee("sergey", "ivanov", 2, 25_000));

        assertEquals(expected.size(),actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void getAll(){
        List<Integer> expectedDepartments = employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toList());

        Map<Integer, List<Employee>> actual = departmentService.getEmployeeGroped();
        assertEquals(expectedDepartments.size(), actual.keySet().size());
        assertTrue(expectedDepartments.containsAll(actual.keySet()));

        verify(employeeService).getAll();
        verify(employeeService,times(0)).remove(anyString(), anyString());
    }
}