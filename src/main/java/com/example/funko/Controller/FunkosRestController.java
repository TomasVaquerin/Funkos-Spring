package com.example.funko.Controller;

import com.example.funko.models.Funko;
import com.example.funko.repository.FunkoRepository; // Importa el repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funkos")
public class FunkosRestController {

    private final FunkoRepository funkoRepository;

    @Autowired
    public FunkosRestController(FunkoRepository funkoRepository) {
        this.funkoRepository = funkoRepository;
    }

    // Obtener todos los Funkos
    @GetMapping
    public ResponseEntity<List<Funko>> getAllFunkos() {
        List<Funko> funkos = funkoRepository.getAllFunkos();
        return new ResponseEntity<>(funkos, HttpStatus.OK);
    }

    // Obtener un Funko por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funko> getFunkoById(@PathVariable Long id) {
        Funko funko = funkoRepository.getFunkoById(id);

        if (funko != null) {
            return new ResponseEntity<>(funko, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un nuevo Funko
    @PostMapping
    public ResponseEntity<Funko> createFunko(@RequestBody Funko funko) {
        Funko createdFunko = funkoRepository.createFunko(funko);
        return new ResponseEntity<>(createdFunko, HttpStatus.CREATED);
    }

    // Actualizar un Funko por ID
    @PutMapping("/{id}")
    public ResponseEntity<Funko> updateFunko(@PathVariable Long id, @RequestBody Funko updatedFunko) {
        Funko existingFunko = funkoRepository.getFunkoById(id);

        if (existingFunko != null) {
            Funko updatedFunkoEntity = funkoRepository.updateFunko(id, updatedFunko);
            return new ResponseEntity<>(updatedFunkoEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar parcialmente un Funko por ID
    @PatchMapping("/{id}")
    public ResponseEntity<Funko> partiallyUpdateFunko(@PathVariable Long id, @RequestBody Funko partialFunko) {
        Funko existingFunko = funkoRepository.getFunkoById(id);

        if (existingFunko != null) {
            Funko updatedFunko = funkoRepository.partiallyUpdateFunko(id, partialFunko);
            return new ResponseEntity<>(updatedFunko, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un Funko por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Funko> deleteFunko(@PathVariable Long id) {
        Funko deletedFunko = funkoRepository.deleteFunko(id);

        if (deletedFunko != null) {
            return new ResponseEntity<>(deletedFunko, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
