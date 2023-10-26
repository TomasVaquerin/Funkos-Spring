package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Funko;

import java.util.List;
import java.util.Optional;

public interface FunkoRepository {
    Funko save(Funko funko);
    Funko update(Funko funko);
    List<Funko> findAll();
    Optional<Funko> findById(Long id);
    void deleteById(Long id);
}
