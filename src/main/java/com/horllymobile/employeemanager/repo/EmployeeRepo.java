package com.horllymobile.employeemanager.repo;

import com.horllymobile.employeemanager.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeById(Long employeeId);
    Optional<Employee> findEmployeeByEmail(String email);
}
