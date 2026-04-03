package com.mika.tasks.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Já existe um usuário cadastrado nesse email!");
    }
}


