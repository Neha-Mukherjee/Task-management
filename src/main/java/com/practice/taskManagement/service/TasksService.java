package com.practice.taskManagement.service;

import com.practice.taskManagement.dto.TasksDto;

import java.util.List;

public interface TasksService {

    TasksDto createTask(TasksDto tasksDto);

    TasksDto getTaskById(Long id);
    List<TasksDto> getTasks(String status);
    TasksDto updateTask(Long id , String status);

    TasksDto assignTask(Long id );


}
