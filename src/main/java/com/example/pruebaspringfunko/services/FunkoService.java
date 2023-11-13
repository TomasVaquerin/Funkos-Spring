package com.example.pruebaspringfunko.services;

import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FunkoService {
    Funko save(FunkoDTOCreUpd funkoDTO);
    Funko update(Long id, FunkoDTOCreUpd funkoDTO);
    Page<Funko> findAll(Optional<String> nombre, Optional<Double> precio, Optional<Integer> cantidad, Optional<String> categoria, Pageable pageable);
    Funko findById(Long id);
    void deleteById(Long id);
}
