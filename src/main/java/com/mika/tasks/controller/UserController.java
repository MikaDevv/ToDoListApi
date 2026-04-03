package com.mika.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mika.tasks.dto.UserRequestDTO;
import com.mika.tasks.dto.UserResponseDTO;
import com.mika.tasks.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO uDto){
        UserResponseDTO u = userService.createUser(uDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
        
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(){

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
    }
}
