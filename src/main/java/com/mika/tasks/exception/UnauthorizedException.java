package com.mika.tasks.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Você não tem permissão para realizar essa ação!");
    }
}
