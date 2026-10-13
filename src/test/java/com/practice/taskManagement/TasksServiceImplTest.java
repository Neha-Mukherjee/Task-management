package com.practice.taskManagement;
import com.practice.taskManagement.dto.TasksDto;
import com.practice.taskManagement.entity.Employee;
import com.practice.taskManagement.entity.Tasks;
import com.practice.taskManagement.mapper.TaskMapper;
import com.practice.taskManagement.repository.EmployeeRepository;
import com.practice.taskManagement.repository.TaskRepository;
import com.practice.taskManagement.service.impl.TasksServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TasksServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TasksServiceImpl tasksService;

    @Test
    void assignTaskWithRequiredSkillAndCapacity(){
        Tasks task = new Tasks();
        task.setTaskId(1L);
        task.setTaskName("Build Python API");
        task.setRequiredSkill("Python");
        task.setPriority("HIGH");
        task.setStatus("WAITING");

        Employee employee = new Employee();
        employee.setEmpId(3L);
        employee.setSkills("Python, SQL");
        employee.setMaxTaskCapacity(4L);

        TasksDto expectedDto = mock(TasksDto.class);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(taskRepository.countByEmployee_EmpIdAndStatus(3L, "ASSIGNED"))
                .thenReturn(1L);
        when(taskRepository.save(any(Tasks.class))).thenAnswer(invocation ->
                invocation.getArgument(0));
        when(taskMapper.toDto(any(Tasks.class))).thenReturn(expectedDto);

        TasksDto result = tasksService.assignTask(1L);

        assertSame(employee, task.getEmployee());
        assertEquals("ASSIGNED", task.getStatus());
        assertSame(expectedDto, result);

        verify(taskRepository).save(task);
    }
}
