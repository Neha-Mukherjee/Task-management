package com.practice.taskManagement.dto;

import com.practice.taskManagement.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record TasksDto(Long taskId,String taskName, String requiredSkill, String priority, String status){
}
//private Long taskId;
//@Column(name="task-name",nullable=false)
//private String taskName;
//@Column(name="requiredSkill",nullable=false)
//private String requiredSkill;
//@Column(name="priority", nullable = false)
//private String priority;
//@Column(name="status",nullable=false)
//private String status;
//@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name="employee_id",nullable = false)
//private Employee employee;