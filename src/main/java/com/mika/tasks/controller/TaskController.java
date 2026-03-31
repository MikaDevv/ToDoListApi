package com.mika.tasks.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mika.tasks.dto.TaskRequestDTO;
import com.mika.tasks.dto.TaskResponseDTO;
import com.mika.tasks.dto.TaskUpdateDTO;
import com.mika.tasks.entity.TaskStatus;
import com.mika.tasks.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskDTO){
        TaskResponseDTO t = taskService.createTask(taskDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(t);

    }
    @GetMapping("/list")
    public ResponseEntity<List<TaskResponseDTO>> listTasks(@RequestParam(value = "status", required = false) TaskStatus status){
        
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksByUser(status));

    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@Valid @PathVariable("id") Long id, @RequestBody TaskUpdateDTO taskUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskById(id, taskUpdateDTO));
        
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@Valid @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTaskById(id));
        
    }
}
