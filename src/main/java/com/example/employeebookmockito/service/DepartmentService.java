package com.example.employeebookmockito.service;

import com.example.employeebookmockito.exceptions.EmployeeNotFoundException;
import com.example.employeebookmockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double getEmployeeSalarySum(int department){
        return employeeService.getAll().stream()
                .filter(e -> e.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    public double getEmployeeMaxSalary(int department){
        return employeeService.getAll().stream()
                .filter(e->e.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .max()
                .orElseThrow(EmployeeNotFoundException::new);
    }
    public double getEmployeeMinSalary(int department){
        return employeeService.getAll().stream()
                .filter(e->e.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .min()
                .orElseThrow(EmployeeNotFoundException::new);
    }
    public List<Employee> getEmployeeByDepartment(int department){
        return employeeService.getAll().stream()
                .filter(e->e.getDepartment() == department)
                .collect(Collectors.toList());
    }
    public Map<Integer,List<Employee>> getEmployeeGroped(){
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
