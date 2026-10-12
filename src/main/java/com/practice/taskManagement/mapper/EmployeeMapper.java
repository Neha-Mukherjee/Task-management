package com.practice.taskManagement.mapper;

import com.practice.taskManagement.dto.EmployeeDto;
import com.practice.taskManagement.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getEmpId(),employee.getFirstName(),employee.getLastName(), employee.getSkills(), employee.getMaxTaskCapacity());
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.empId(),employeeDto.firstName(),employeeDto.lastName(),employeeDto.skills(),employeeDto.maxTaskCapacity(),true);
    }
}
//
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name="employee_id", nullable = false)
//    private Long empId;
//    @Column(name="first-name", nullable = false)
//    private String firstName;
//    @Column(name="last-name", nullable = false)
//    private String lastName;
/// /    @Column(name="email", nullable = false)
/// /    private String email;
//    @Column(name="skills", nullable = false)
//    private String skills;