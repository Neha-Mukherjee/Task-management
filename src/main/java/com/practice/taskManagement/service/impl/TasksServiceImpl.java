package com.practice.taskManagement.service.impl;

import com.practice.taskManagement.auth.exception.ResourceNotFoundException;
import com.practice.taskManagement.dto.TasksDto;
import com.practice.taskManagement.entity.Employee;
import com.practice.taskManagement.entity.Tasks;
import com.practice.taskManagement.mapper.TaskMapper;
import com.practice.taskManagement.repository.EmployeeRepository;
import com.practice.taskManagement.repository.TaskRepository;
import com.practice.taskManagement.service.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final EmployeeRepository employeeRepository;
    @Override
    public TasksDto createTask(TasksDto tasksDto) {
       Tasks task= taskMapper.toEntity(tasksDto);
       task.setStatus("WAITING");
        Tasks savedTask=taskRepository.save(task);
        return assignTask(savedTask.getTaskId());
    }
    @Override
    public TasksDto getTaskById(Long id) {
        Tasks tasks = taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Task not found"));

        return taskMapper.toDto(tasks);
    }

    @Override
    public List<TasksDto> getTasks(String status) {
        if(status==null|| status.isBlank()){
            return taskRepository.findAll().stream().map(taskMapper::toDto).toList();
        }
        return taskRepository.findByStatus(status).stream().map(taskMapper::toDto).toList();
    }
    @Override
    public TasksDto updateTask(Long id , String status) {
        Tasks tasks = taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Task not found"));
        if ("COMPLETED".equalsIgnoreCase(tasks.getStatus())) {
            throw new IllegalArgumentException("Task is already completed");
        }
        tasks.setStatus(status);
        Tasks updatedTask = taskRepository.save(tasks);
        if("COMPLETED".equalsIgnoreCase(status)){
          assignNextWaitingTask();
        }
        return taskMapper.toDto(updatedTask);
    }

    @Override
    public TasksDto assignTask(Long id) {
        Employee bestEmployee = null;
        long lowestWorkload = Long.MAX_VALUE;
        Tasks tasks = taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Task not found"));
        if ("ASSIGNED".equalsIgnoreCase(tasks.getStatus())) {
            throw new IllegalArgumentException("Task is already assigned");
        }

        if ("COMPLETED".equalsIgnoreCase(tasks.getStatus())) {
            throw new IllegalArgumentException("Completed task cannot be reassigned");
        }
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee:employees){

            if(!hasRequiredSkill(employee,tasks)) continue ;
            if(getActiveTaskCount(employee)>=employee.getMaxTaskCapacity()) continue;
           if (getActiveTaskCount(employee)<lowestWorkload){
               lowestWorkload = getActiveTaskCount(employee);
               bestEmployee = employee;
           }
        }
        if (bestEmployee != null) {
            tasks.setEmployee(bestEmployee);
            tasks.setStatus("ASSIGNED");
        } else {
            boolean skillAvailable = employees.stream()
                    .anyMatch(employee ->
                            hasRequiredSkill(employee, tasks));

            if (!skillAvailable) {
                throw new IllegalArgumentException(
                        "No employee available with required skill: "
                                + tasks.getRequiredSkill());
            }

            tasks.setEmployee(null);
            tasks.setStatus("WAITING");
        }
//        tasks.setEmployee(bestEmployee);
//        tasks.setStatus("ASSIGNED");
        Tasks savedTask = taskRepository.save(tasks);

        return taskMapper.toDto(savedTask);
    }
    private boolean hasRequiredSkill(Employee employee, Tasks task) {
        return Arrays.stream(employee.getSkills().split(","))
                .map(String::trim)
                .anyMatch(skill ->
                        skill.equalsIgnoreCase(task.getRequiredSkill()));
    }
    private long getActiveTaskCount(Employee employee) {
        return taskRepository.countByEmployee_EmpIdAndStatus(
                employee.getEmpId(), "ASSIGNED");
    }

    private void assignNextWaitingTask(){
        List<Tasks> waitingTasks = taskRepository.findByStatusOrderByPriorityAsc("WAITING");


        List<String> priorityOrder = List.of("HIGH", "MEDIUM", "LOW");
        waitingTasks = waitingTasks.stream()
                .sorted((t1, t2) -> Integer.compare(
                        priorityOrder.indexOf(t1.getPriority()),
                        priorityOrder.indexOf(t2.getPriority())
                ))
                .toList();
        for (Tasks task : waitingTasks) {

            boolean skillAvailable = employeeRepository.findAll()
                    .stream()
                    .anyMatch(employee -> hasRequiredSkill(employee, task));

            if (!skillAvailable) {
                continue;
            }

            TasksDto assignedTask = assignTask(task.getTaskId());

            if ("ASSIGNED".equalsIgnoreCase(assignedTask.status())) {
                return;
            }
        }
    }
}
