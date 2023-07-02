package com.example.employeebookmockito.controller;

import com.example.employeebookmockito.model.Employee;
import com.example.employeebookmockito.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping(value ="/salary/sum")
    public double getSum(@RequestParam("departmentId")int department){
        return departmentService.getEmployeeSalarySum(department);
    }
    @GetMapping(value = "/salary/max")
    public double getMax(@RequestParam("departmentId") int department){
        return departmentService.getEmployeeMaxSalary(department);
    }
    @GetMapping(value = "/salary/min")
    public double getMin(@RequestParam("departmentId") int department){
        return departmentService.getEmployeeMinSalary(department);
    }
    @GetMapping(value = "/employees")
    private List<Employee> getAll(@RequestParam("departmentId")int department){
        return departmentService.getEmployeeByDepartment(department);
    }
    @GetMapping(value = "/employees")
    public Map<Integer,List<Employee>> getAll(){
        return departmentService.getEmployeeGroped();
    }
}