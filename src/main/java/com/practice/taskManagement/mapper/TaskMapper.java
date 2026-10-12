package com.practice.taskManagement.mapper;


import com.practice.taskManagement.dto.TasksDto;
import com.practice.taskManagement.entity.Employee;
import com.practice.taskManagement.entity.Tasks;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TasksDto toDto(Tasks tasks) {
        return new TasksDto(tasks.getTaskId(),  tasks.getTaskName(), tasks.getRequiredSkill(), tasks.getPriority(), tasks.getStatus());
    }

    public Tasks toEntity(TasksDto tasksDto) {
        return new Tasks(tasksDto.taskId(), tasksDto.taskName(), tasksDto.requiredSkill(), tasksDto.priority(), tasksDto.status(),null );
    }
}
//    private Long taskId;
//    @Column(name="task-name",nullable=false)
//    private String taskName;
//    @Column(name="requiredSkill",nullable=false)
//    private String requiredSkill;
//    @Column(name="priority", nullable = false)
//    private String priority;
//    @Column(name="status",nullable=false)
//    private String status;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="employee_id",nullable = false)
//    private Employee employee;