package com.example.pruebaspringfunko.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequesException extends FunkoException{
    public BadRequesException(String mensaje) {
        super(mensaje);
    }
}
