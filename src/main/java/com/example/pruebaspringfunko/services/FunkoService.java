package com.example.pruebaspringfunko.services;

import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;

import java.util.List;
import java.util.Optional;

public interface FunkoService {
    Funko save(FunkoDTOCreUpd funkoDTO);
    Funko update(Long id, FunkoDTOCreUpd funkoDTO);
    List<Funko> findAll();
    Funko findById(Long id);
    void deleteById(Long id);
}
