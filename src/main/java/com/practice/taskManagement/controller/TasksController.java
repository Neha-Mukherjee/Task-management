package com.practice.taskManagement.controller;

import com.practice.taskManagement.dto.TasksDto;
import com.practice.taskManagement.service.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task-management/tasks")
public class TasksController {


    private final TasksService tasksService;


    @PostMapping
    public ResponseEntity<TasksDto> createTask(@RequestBody TasksDto tasksDto) {
        TasksDto savedTask=tasksService.createTask(tasksDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }


    @GetMapping
    public ResponseEntity<List<TasksDto>> getTasks(@RequestParam(required = false) String status) {
        List<TasksDto> tasks=tasksService.getTasks(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasksDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(tasksService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasksDto> updateTask(@PathVariable("id") Long id ,@RequestParam String status) {
        TasksDto taskdto = tasksService.updateTask(id,status);
        return ResponseEntity.ok(taskdto);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity assignTask(@PathVariable Long id ){
        TasksDto taskdto = tasksService.assignTask(id);
        return ResponseEntity.ok(taskdto);
    }

}
