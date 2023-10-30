package com.example.pruebaspringfunko.exceptions;

public class FunkoException extends RuntimeException{
    public FunkoException(String mensaje){
        super(mensaje);
    }
}
