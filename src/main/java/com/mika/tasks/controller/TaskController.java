package com.mika.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mika.tasks.dto.TaskDTO;
import com.mika.tasks.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO){
        TaskDTO t = taskService.createTask(taskDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(t);

    }
}
