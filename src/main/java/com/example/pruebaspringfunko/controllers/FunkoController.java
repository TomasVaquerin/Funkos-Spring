package com.example.pruebaspringfunko.controllers;

import com.example.pruebaspringfunko.mapper.FunkoMapper;
import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;
import com.example.pruebaspringfunko.services.FunkoServiceImpl;
import com.example.pruebaspringfunko.storage.services.FileSystemStorageService;
import com.example.pruebaspringfunko.utils.PaginacionLinks;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FunkoController {
    private final FunkoServiceImpl funkoService;
    private FileSystemStorageService storageService;
    private final PaginacionLinks paginacionLinks;

    @Autowired
    public FunkoController(FunkoServiceImpl funkoService, PaginacionLinks paginacionLinks) {
        this.funkoService = funkoService;
        this.paginacionLinks = paginacionLinks;
    }

    @GetMapping("/funks")
    public ResponseEntity<Page<Funko>> getAll(
            @RequestParam(required = false) Optional<String> nombre,
            @RequestParam(required = false) Optional<Double> precio,
            @RequestParam(required = false) Optional<Integer> cantidad,
            @RequestParam(required = false) Optional<String> categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            HttpServletRequest request
    ){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        Page<Funko> pageResult = funkoService.findAll(nombre, precio, cantidad, categoria, PageRequest.of(page, size, sort));
        return ResponseEntity.ok()
                .header("link", paginacionLinks.createLinkHeader(pageResult, uri))
                .body(pageResult);
    }

    @GetMapping("/funks/{id}")
    public ResponseEntity<Funko> getId(@PathVariable Long id){
        return ResponseEntity.ok(funkoService.findById(id));
    }

    @PostMapping("/funks")
    public ResponseEntity<FunkoDTOCreUpd> create(@Valid @RequestBody FunkoDTOCreUpd funkoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                FunkoMapper.convertirFunkoaDTO(
                        funkoService.save(funkoDTO)));
    }

    @PutMapping("/funks/{id}")
    public ResponseEntity<FunkoDTOCreUpd> update(@PathVariable Long id, @Valid @RequestBody FunkoDTOCreUpd updatedFunkoDTO){
        return ResponseEntity.ok(
                FunkoMapper.convertirFunkoaDTO(
                        funkoService.update(id, updatedFunkoDTO)));
    }

    @PatchMapping("/funks/upload/{id}")
    public ResponseEntity<FunkoDTOCreUpd> uploadImage(@PathVariable Long id, @RequestPart("file") MultipartFile file, @Valid @RequestBody FunkoDTOCreUpd updatedFunkoDTO) throws IOException {
        String imageUrl = storageService.store(file);
        updatedFunkoDTO.setImagen(imageUrl);
        return ResponseEntity.ok(
                FunkoMapper.convertirFunkoaDTO(
                        funkoService.update(id, updatedFunkoDTO)));
    }

    @DeleteMapping("/funks/{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id){
        funkoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
