package com.example.employeebookmockito.service;

import com.example.employeebookmockito.exceptions.EmployeeAlreadyAddedException;
import com.example.employeebookmockito.exceptions.EmployeeNotFoundException;
import com.example.employeebookmockito.exceptions.EmployeeStorageIsFullException;
import com.example.employeebookmockito.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    EmployeeService employeeService = new EmployeeService();
    @Test
    void getAll(){
        Employee e1 = new Employee("Ivan", "Ivanov",1,10000.0);
        Employee e2 = new Employee("Marina", "Ivanova",2,20000.0);
        employeeService.add(e1);
        employeeService.add(e2);
        List<Employee> expected = Arrays.asList(e1,e2);

        assertEquals(2, employeeService.getAll().size());
        assertIterableEquals(expected, employeeService.getAll());
    }

    @Test
    void add(){
        int prevSize = employeeService.getAll().size();

        Employee e1 = new Employee("Ivan", "Ivanov",1,10000.0);
        employeeService.add(e1);

        assertEquals(prevSize + 1, employeeService.getAll().size());
        assertTrue(employeeService.getAll().contains(e1));
    }

    @Test
    void whenAddDuplicateThenThrowsException(){
        Employee e1 = new Employee("Ivan", "Ivanov",1,10000.0);
        assertDoesNotThrow(() -> employeeService.add(e1));
        assertThrows(EmployeeAlreadyAddedException.class, () -> employeeService.add(e1));
    }

    @Test
    void whenStorageIsFullThenThrowException() {
        for (int i = 0; i < 5; i++) {
            Employee e = new Employee("firstName_" + i,"lastName_" + i,1,10000.0);
            assertDoesNotThrow(() -> employeeService.add(e));
        }
        assertThrows(EmployeeStorageIsFullException.class,
                    () -> employeeService.add(new Employee("Ivan", "Ivanov", 1, 10000.0)));
    }

    @Test
    void findPositive(){
        Employee expected = new Employee("Ivan", "Ivanov",1,10000.0);
        employeeService.add(expected);
        Employee actual = employeeService.find("Ivan", "Ivanov");
        assertNotNull(actual);
        assertEquals(expected,actual);
    }

    @Test
    void findNegative(){
        Employee expected = new Employee("Ivan", "Ivanov",1,10000.0);
        employeeService.add(expected);
        assertThrows(EmployeeNotFoundException.class,() -> employeeService.find("Marina","Ivanova"));
    }

    @Test
    void remove(){
        Employee e = new Employee("Ivan", "Ivanov",1,10000.0);
        employeeService.add(e);

        assertTrue(employeeService.getAll().contains(e));

        employeeService.remove("Ivan","Ivanov");
        assertFalse(employeeService.getAll().contains(e));
    }
}