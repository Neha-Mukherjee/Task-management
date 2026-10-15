package com.practice.taskManagement;
import com.practice.taskManagement.auth.exception.ResourceNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
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
    @Test
    void assignTaskWhenNoEmployeeHasRequiredSkill(){
        Tasks task = new Tasks();
        task.setTaskId(1L);
        task.setTaskName("Build website using javascript");
        task.setRequiredSkill("JavaScript");
        task.setPriority("HIGH");
        task.setStatus("WAITING");

        Employee employee = new Employee();
        employee.setEmpId(3L);
        employee.setSkills(" SQL");
        employee.setMaxTaskCapacity(4L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(employeeRepository.findAll()).thenReturn(List.of(employee));


        assertThrows(IllegalArgumentException.class, () ->tasksService.assignTask(1L));



    }
    @Test
    void assignTaskWhenEmployeeCapacityIsFull(){
        Tasks task = new Tasks();
        task.setTaskId(1L);
        task.setTaskName("Build website ");
        task.setRequiredSkill("JAVA");
        task.setPriority("HIGH");
        task.setStatus("WAITING");
        Employee employee = new Employee();
        employee.setEmpId(3L);
        employee.setSkills("JAVA");
        employee.setMaxTaskCapacity(2L);

       // TasksDto expectedDto = mock(TasksDto.class);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.countByEmployee_EmpIdAndStatus(3L, "ASSIGNED")).thenReturn(2L);
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
       // when(taskMapper.toDto(task)).thenReturn(expectedDto);
        TasksDto result = tasksService.assignTask(1L);
        assertNull(task.getEmployee());
        assertEquals("WAITING", task.getStatus());


    }
    @Test
    void assignTaskToEmployeeWithLowestWorkload() {
        Tasks task = new Tasks();
        task.setTaskId(10L);
        task.setRequiredSkill("JAVA");
        task.setStatus("WAITING");

        Employee emp1 = new Employee();
        emp1.setEmpId(1L);
        emp1.setSkills("JAVA");
        emp1.setMaxTaskCapacity(5L);

        Employee emp2 = new Employee();
        emp2.setEmpId(2L);
        emp2.setSkills("JAVA");
        emp2.setMaxTaskCapacity(5L);

        when(taskRepository.findById(10L))
                .thenReturn(Optional.of(task));
        when(employeeRepository.findAll())
                .thenReturn(List.of(emp1, emp2));
        when(taskRepository.countByEmployee_EmpIdAndStatus(1L, "ASSIGNED"))
                .thenReturn(2L);
        when(taskRepository.countByEmployee_EmpIdAndStatus(2L, "ASSIGNED"))
                .thenReturn(0L);
        when(taskRepository.save(any(Tasks.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toDto(any(Tasks.class)))
                .thenReturn(mock(TasksDto.class));

        tasksService.assignTask(10L);

        assertSame(emp2, task.getEmployee());
        assertEquals("ASSIGNED", task.getStatus());
    }

    @Test
    void assignTaskWhenTaskDoesNotExist() {
        when(taskRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> tasksService.assignTask(99L));
    }
    @Test
    void assignTaskWhenAlreadyAssigned() {
        Tasks task = new Tasks();
        task.setTaskId(1L);
        task.setStatus("ASSIGNED");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        assertThrows(IllegalArgumentException.class,
                () -> tasksService.assignTask(1L));

        verify(taskRepository, never()).save(any(Tasks.class));
    }
    @Test
    void updateTaskToCompleted() {
        Tasks task = new Tasks();
        task.setTaskId(1L);
        task.setStatus("ASSIGNED");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        when(taskRepository.save(any(Tasks.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toDto(any(Tasks.class)))
                .thenReturn(mock(TasksDto.class));

        tasksService.updateTask(1L, "COMPLETED");

        assertEquals("COMPLETED", task.getStatus());
        verify(taskRepository).save(task);
    }
}
