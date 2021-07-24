package com.horllymobile.employeemanager.service;

import com.horllymobile.employeemanager.contracts.request.CreateEmployeeRequest;
import com.horllymobile.employeemanager.contracts.request.UpdateEmployeeRequest;
import com.horllymobile.employeemanager.contracts.response.CreateEmployeeResponse;
import com.horllymobile.employeemanager.contracts.response.ResponseError;
import com.horllymobile.employeemanager.contracts.response.SuccessResponse;
import com.horllymobile.employeemanager.exception.EmailNotFoundException;
import com.horllymobile.employeemanager.exception.UserNotFoundException;
import com.horllymobile.employeemanager.models.Employee;
import com.horllymobile.employeemanager.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Handler;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }


    public List<Employee> getEmployees(){
        return employeeRepo.findAll();
    }

    public Employee findEmployee(Long employeeId){
        return employeeRepo.findEmployeeById(employeeId).orElseThrow(
                () -> new UserNotFoundException("User by id "+ employeeId + " was not found")
        );
    }
    public ResponseEntity<?> addEmployee(CreateEmployeeRequest employee){
        if(employee.getEmail().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("email is required"), HttpStatus.BAD_REQUEST);
        }
        Optional<Employee> employeeOptional = employeeRepo.findEmployeeByEmail(employee.getEmail());

        if(employeeOptional.isPresent()){
            return new ResponseEntity<ResponseError>(new ResponseError("employee with email already exist"), HttpStatus.CONFLICT);
        }

        if(employee.getPhone().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Phone number is required"), HttpStatus.BAD_REQUEST);
        }

        if(employee.getJobTitle().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Job title is required"), HttpStatus.BAD_REQUEST);
        }

        if(employee.getImageUrl().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Employee image is required"), HttpStatus.BAD_REQUEST);
        }

        if(employee.getName().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Employee name is required"), HttpStatus.BAD_REQUEST);
        }
        Employee employee1 = new Employee(
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getJobTitle(),
                employee.getImageUrl(),
                UUID.randomUUID());

        employeeRepo.save(employee1);
        return new ResponseEntity<CreateEmployeeResponse>
                (new CreateEmployeeResponse("Employee added successfully"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateEmployee(UpdateEmployeeRequest employee){
        if(employee.getEmail().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("email is required"), HttpStatus.BAD_REQUEST);
        }
        if(employee.getPhone().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Phone number is required"), HttpStatus.BAD_REQUEST);
        }
        if(employee.getJobTitle().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Job title is required"), HttpStatus.BAD_REQUEST);
        }
        if(employee.getImageUrl().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Employee image is required"), HttpStatus.BAD_REQUEST);
        }
        if(employee.getName().isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Employee name is required"), HttpStatus.BAD_REQUEST);
        }

        Optional<Employee> employeeOptional = employeeRepo.findEmployeeById(employee.getId());

        if(employeeOptional.isPresent()){
            Optional<Employee> employeeOptional1 = employeeRepo.findEmployeeByEmail(employee.getEmail());
            if(employeeOptional1.isPresent() && !employeeOptional.get().getEmail().equals(employeeOptional1.get().getEmail())) {
                return new ResponseEntity<ResponseError>(new ResponseError("employee with email already exist"), HttpStatus.CONFLICT);
            }
        }

        Employee employee1 = employeeRepo.getOne(employee.getId());

        employee1.setName(employee.getName());
        employee1.setEmail(employee.getEmail());
        employee1.setPhone(employee.getPhone());
        employee1.setJobTitle(employee.getJobTitle());
        employee1.setImageUrl(employee.getImageUrl());

        employeeRepo.save(employee1);
        return new ResponseEntity<CreateEmployeeResponse>
                (new CreateEmployeeResponse("Employee updated successfully"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteEmployee(Long employeeId){
        Optional<Employee> employeeOptional = employeeRepo.findEmployeeById(employeeId);
        if(employeeOptional.isEmpty()){
            return new ResponseEntity<ResponseError>(new ResponseError("Employee with id "+ employeeId + " not found"), HttpStatus.NOT_FOUND);
        }
        Employee employee = employeeRepo.getOne(employeeId);
        employeeRepo.delete(employee);
        return new ResponseEntity<SuccessResponse>(new SuccessResponse("Employee successfully deleted"), HttpStatus.OK);
    }
}
