package com.horllymobile.employeemanager.controller;


import com.horllymobile.employeemanager.contracts.request.CreateEmployeeRequest;
import com.horllymobile.employeemanager.contracts.request.UpdateEmployeeRequest;
import com.horllymobile.employeemanager.models.Employee;
import com.horllymobile.employeemanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee findEmployee(@PathVariable("id") Long id){
        return employeeService.findEmployee(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> addEmployee(@RequestBody CreateEmployeeRequest employee){
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeRequest request){
        return employeeService.updateEmployee(request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
         return employeeService.deleteEmployee(id);
    }
}
