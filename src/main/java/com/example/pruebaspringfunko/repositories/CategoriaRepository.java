package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNameEqualsIgnoreCase(String name);
}
