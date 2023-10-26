package com.example.funko.repository;

import com.example.funko.models.Funko;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FunkoRepository {
    private final List<Funko> funkos = new ArrayList<>();

    public List<Funko> getAllFunkos() {
        return funkos;
    }

    public Optional<Funko> getFunkoById(Long id) {
        return funkos.stream()
                .filter(funko -> funko.getId().equals(id))
                .findFirst();
    }

    public Optional<Funko> createFunko(Funko funko) {
        funko.setId((long) (funkos.size() + 1)); // Asigna un ID único
        funko.setFechaCreacion(LocalDateTime.now());
        funko.setFechaActualizacion(LocalDateTime.now());
        funkos.add(funko);
        return Optional.of(funko);
    }

    public Optional<Funko> updateFunko(Long id, Funko updatedFunko) {
        Optional<Funko> existingFunko = getFunkoById(id);
        if (existingFunko.isPresent()) {
            updatedFunko.setId(id);
            updatedFunko.setFechaCreacion(existingFunko.get().getFechaCreacion());
            updatedFunko.setFechaActualizacion(LocalDateTime.now());
            int index = funkos.indexOf(existingFunko.get());
            funkos.set(index, updatedFunko);
        }
        return existingFunko;
    }

    public Optional<Funko> deleteFunko(Long id) {
        Optional<Funko> existingFunko = getFunkoById(id);
        existingFunko.ifPresent(funkos::remove);
        return existingFunko;
    }

    public Optional<Funko> partiallyUpdateFunko(Long id, Funko partialFunko) {
        Optional<Funko> existingFunko = getFunkoById(id);

        if (existingFunko.isPresent()) {
            if (partialFunko.getNombre() != null) {
                existingFunko.get().setNombre(partialFunko.getNombre());
            }
            if (partialFunko.getPrecio() != 0.0) {
                existingFunko.get().setPrecio(partialFunko.getPrecio());
            }
            if (partialFunko.getCantidad() != 0) {
                existingFunko.get().setCantidad(partialFunko.getCantidad());
            }
            if (partialFunko.getImagen() != null) {
                existingFunko.get().setImagen(partialFunko.getImagen());
            }
            if (partialFunko.getCategoria() != null) {
                existingFunko.get().setCategoria(partialFunko.getCategoria());
            }
            existingFunko.get().setFechaActualizacion(LocalDateTime.now());

            return existingFunko;
        } else {
            return Optional.empty();
        }
    }
}
