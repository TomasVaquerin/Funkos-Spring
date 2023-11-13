package com.example.pruebaspringfunko.storage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StorageNotFoundException extends StorageException{
    public StorageNotFoundException(String mensaje) {
        super(mensaje);
    }
}
