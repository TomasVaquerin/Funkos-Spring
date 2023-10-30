package com.example.pruebaspringfunko.controllers;

import com.example.pruebaspringfunko.mapper.FunkoMapper;
import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;
import com.example.pruebaspringfunko.services.FunkoServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/funkos")
public class FunkoController {
    private final FunkoServiceImpl funkoService;

    @Autowired
    public FunkoController(FunkoServiceImpl funkoService) {
        this.funkoService = funkoService;
    }

    @GetMapping()
    public ResponseEntity<List<Funko>> getAll(@RequestParam(required = false) Funko.Categoria categoria){
        if (categoria != null) {
            return ResponseEntity.ok(funkoService.getFunkosPorCategoria(categoria));
        } else {
            return ResponseEntity.ok(funkoService.findAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funko> getId(@PathVariable Long id){
        return ResponseEntity.ok(funkoService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<FunkoDTOCreUpd> create(@Valid @RequestBody FunkoDTOCreUpd funkoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                FunkoMapper.convertFunkDTO(
                        funkoService.save(funkoDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FunkoDTOCreUpd> update(@PathVariable Long id, @Valid @RequestBody FunkoDTOCreUpd updatedFunkoDTO){
        return ResponseEntity.ok(
                FunkoMapper.convertFunkDTO(
                        funkoService.update(id, updatedFunkoDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id){
        funkoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return errors;
    }
}
