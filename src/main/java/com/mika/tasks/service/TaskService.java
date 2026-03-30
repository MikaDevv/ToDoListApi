package com.mika.tasks.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mika.tasks.entity.User;
import com.mika.tasks.dto.TaskDTO;
import com.mika.tasks.entity.Task;
import com.mika.tasks.entity.TaskStatus;
import com.mika.tasks.repository.TaskRepository;
import com.mika.tasks.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    
    public TaskDTO createTask(TaskDTO taskDTO){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> resultado = userRepository.findByEmail(email);
        User user = resultado.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Task t = new Task();
        t.setTitle(taskDTO.title());
        t.setDescription(taskDTO.description());
        t.setUser(user);
        t.setTaskStatus(TaskStatus.PENDING);
        taskRepository.save(t);
        TaskDTO taskDTO2 = new TaskDTO(taskDTO.title(), taskDTO.description());
        return taskDTO2;
        
    }

    }


