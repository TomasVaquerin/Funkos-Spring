package com.example.pruebaspringfunko.storage.controller;

import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.storage.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/storage")
public class StorageController {
    private final StorageService storageService;
    private Funko funko;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/{filename}")
    private ResponseEntity<Resource> geteFile(@PathVariable String filename) throws MalformedURLException {
        Resource resource = storageService.loadAsResource(filename);
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/upload")
    private ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            funko.setImagen(imagen);
            return ResponseEntity.status(HttpStatus.CREATED).body(imagen);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede subir un fichero vacío");
        }
    }

}
