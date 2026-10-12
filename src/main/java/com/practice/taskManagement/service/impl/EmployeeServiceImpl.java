package com.practice.taskManagement.service.impl;

import com.practice.taskManagement.auth.exception.ResourceNotFoundException;
import com.practice.taskManagement.dto.EmployeeDto;
import com.practice.taskManagement.entity.Employee;
import com.practice.taskManagement.mapper.EmployeeMapper;
import com.practice.taskManagement.repository.EmployeeRepository;
import com.practice.taskManagement.service.EmployeeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee=employeeMapper.toEntity(employeeDto);
        Employee savedEmployee=employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id "+id));
        return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees=employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toDto).toList();
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id "+id));
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setSkills(employeeDto.skills());
        employee.setMaxTaskCapacity(employeeDto.maxTaskCapacity());
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public void deactivateEmployee(Long id) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with id "+id));

        employee.setActive(false);
        employeeRepository.save(employee);


    }
}
