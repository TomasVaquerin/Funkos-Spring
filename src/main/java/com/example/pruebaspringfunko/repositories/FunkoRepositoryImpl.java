package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Funko;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class FunkoRepositoryImpl implements FunkoRepository{
    List<Funko> funkos;

    public FunkoRepositoryImpl() {
        funkos = loadLista();
    }

    @Override
    public Funko save(Funko funko) {
        funkos.add(funko);
        return funko;
    }

    @Override
    public Funko update(Funko funko) {
        return funko;
    }

    @Override
    public List<Funko> findAll() {
        return funkos;
    }

    @Override
    public Optional<Funko> findById(Long id) {
        for (Funko funko : funkos) {
            if (Objects.equals(funko.getId(), id)) {
                return Optional.of(funko);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        funkos.removeIf(
                funko -> Objects.equals(funko.getId(), id)
        );
    }

    public List<Funko> getFunkosPorCategoria(Funko.Categoria categoria) {
        return funkos.stream()
                .filter(funko -> funko.getCategoria() == categoria)
                .toList();
    }

    public List<Funko> loadLista() {
        funkos = new ArrayList<>();
        funkos.add(Funko.builder()
                .nombre("Spiderman")
                .precio(15.99)
                .cantidad(5)
                .categoria(Funko.Categoria.MARVEL)
                .build());

        funkos.add(Funko.builder()
                .nombre("Iron Man")
                .precio(12.99)
                .cantidad(10)
                .categoria(Funko.Categoria.MARVEL)
                .build());

        funkos.add(Funko.builder()
                .nombre("Captain America")
                .precio(14.99)
                .cantidad(7)
                .categoria(Funko.Categoria.MARVEL)
                .build());

        funkos.add(Funko.builder()
                .nombre("Mickey Mouse")
                .precio(10.99)
                .cantidad(15)
                .categoria(Funko.Categoria.DISNEY)
                .build());

        funkos.add(Funko.builder()
                .nombre("Minnie Mouse")
                .precio(12.99)
                .cantidad(8)
                .categoria(Funko.Categoria.DISNEY)
                .build());

        funkos.add(Funko.builder()
                .nombre("Donald Duck")
                .precio(9.99)
                .cantidad(20)
                .categoria(Funko.Categoria.DISNEY)
                .build());
        return funkos;
    }

}
