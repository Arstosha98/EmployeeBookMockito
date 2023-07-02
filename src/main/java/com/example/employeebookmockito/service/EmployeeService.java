package com.example.employeebookmockito.service;

import com.example.employeebookmockito.exceptions.EmployeeAlreadyAddedException;
import com.example.employeebookmockito.exceptions.EmployeeNotFoundException;
import com.example.employeebookmockito.exceptions.EmployeeStorageIsFullException;
import com.example.employeebookmockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private static final int SIZE_LIMIT = 5;
    private final Map<String, Employee> employees = new HashMap<>(SIZE_LIMIT);
    public EmployeeService(){
    }

    public Collection<Employee> getAll(){
        return employees.values();
    }

    public Employee add(Employee employee){
        if (employees.size()>= SIZE_LIMIT){
            throw new EmployeeStorageIsFullException();
        }
        if(employees.containsKey(createKey(employee))){
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(createKey(employee), employee);
        return employee;
    }
    public Employee find(String firstName, String lastName){
        Employee employee = employees.get(createKey(firstName, lastName));
        if (employee == null){
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Employee remove(String firstName, String lastName){
        return employees.remove(createKey(firstName, lastName));
    }

    private static String createKey(Employee employee){
        return createKey(employee.getFirstName(), employee.getLastName());
    }
    private static String createKey(String firstName, String lastName) {
        return (firstName + lastName).toLowerCase();
    }
}
