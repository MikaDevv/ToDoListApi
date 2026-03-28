package com.mika.tasks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRequestDTO( 

    
    @NotBlank(message = "O nome nao pode ser vazio")
    String name,
    
    @NotBlank(message = "O email nao pode ser vazio")
    @Email
    String email,
    
    @NotBlank
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    String password
)
{}