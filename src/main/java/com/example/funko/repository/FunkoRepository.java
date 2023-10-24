package com.example.funko.repository;

import com.example.funko.models.Funko;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FunkoRepository {
    private final List<Funko> funkos = new ArrayList<>();

    public List<Funko> getAllFunkos() {
        return funkos;
    }

    public Funko getFunkoById(Long id) {
        return funkos.stream()
                .filter(funko -> funko.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Funko createFunko(Funko funko) {
        funko.setId((long) (funkos.size() + 1)); // Asigna un ID único
        funko.setFechaCreacion(LocalDate.now());
        funko.setFechaActualizacion(LocalDate.now());
        funkos.add(funko);
        return funko;
    }

    public Funko updateFunko(Long id, Funko updatedFunko) {
        Funko existingFunko = getFunkoById(id);
        if (existingFunko != null) {
            updatedFunko.setId(id);
            updatedFunko.setFechaCreacion(existingFunko.getFechaCreacion());
            updatedFunko.setFechaActualizacion(LocalDate.now());
            int index = funkos.indexOf(existingFunko);
            funkos.set(index, updatedFunko);
        }
        return existingFunko;
    }

    public Funko deleteFunko(Long id) {
        Funko existingFunko = getFunkoById(id);
        if (existingFunko != null) {
            funkos.remove(existingFunko);
        }
        return existingFunko;
    }

    public Funko partiallyUpdateFunko(Long id, Funko partialFunko) {
        Funko existingFunko = getFunkoById(id);

        if (existingFunko != null) {
            if (partialFunko.getNombre() != null) {
                existingFunko.setNombre(partialFunko.getNombre());
            }
            if (partialFunko.getPrecio() != 0.0) {
                existingFunko.setPrecio(partialFunko.getPrecio());
            }
            if (partialFunko.getCantidad() != 0) {
                existingFunko.setCantidad(partialFunko.getCantidad());
            }
            if (partialFunko.getImagen() != null) {
                existingFunko.setImagen(partialFunko.getImagen());
            }
            if (partialFunko.getCategoria() != null) {
                existingFunko.setCategoria(partialFunko.getCategoria());
            }
            existingFunko.setFechaActualizacion(LocalDate.now());

            return existingFunko;
        } else {
            return null;
        }
    }
}
