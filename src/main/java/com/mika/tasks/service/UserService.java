package com.mika.tasks.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mika.tasks.dto.UserRequestDTO;
import com.mika.tasks.dto.UserResponseDTO;
import com.mika.tasks.entity.User;
import com.mika.tasks.exception.UserAlreadyExistsException;
import com.mika.tasks.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder security;
    
    public UserResponseDTO createUser(UserRequestDTO userDto){
        Optional<User> resultado = userRepository.findByEmail(userDto.email());
        if(!resultado.isPresent()){
            User u = new User();
            u.setEmail(userDto.email());
            u.setName(userDto.name());
            String hashedPassword = security.encode(userDto.password());
            u.setPassword(hashedPassword);
            userRepository.save(u);
            UserResponseDTO userResponseDTO = new UserResponseDTO(userDto.name(), userDto.email());
            return userResponseDTO;
            
        }else{
            throw new UserAlreadyExistsException();
        }

    }

}
