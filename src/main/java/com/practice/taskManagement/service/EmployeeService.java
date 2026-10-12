package com.practice.taskManagement.service;

import com.practice.taskManagement.dto.EmployeeDto;
import com.practice.taskManagement.entity.Employee;

import java.util.List;

public interface EmployeeService {


    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Long id , EmployeeDto employeeDto);
    void deactivateEmployee(Long id);
}
