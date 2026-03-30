package com.mika.tasks.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mika.tasks.entity.User;
import com.mika.tasks.dto.AuthDTO;
import com.mika.tasks.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder security;
    private final JwtService jwtService;

        public String login(AuthDTO authDTO){
        Optional<User> resultado = userRepository.findByEmail(authDTO.email());
        if(resultado.isPresent()){
            User u = resultado.get();
            if(security.matches(authDTO.password(), u.getPassword())){
                return jwtService.generateToken(authDTO.email());
            }else throw new RuntimeException("Senha ou email incorretos!");
            
        }else{
            throw new RuntimeException("Usuário não cadastrado!");
        }

    }
    
}
