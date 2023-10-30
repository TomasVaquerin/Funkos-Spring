package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Categoria;
import com.example.pruebaspringfunko.models.Funko;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;

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

    public List<Funko> getFunkosPorCategoria(Funko.Categoria.tipoCategoria categoria) {
        return funkos.stream()
                .filter(funko -> funko.getCategoria().getTipoCategoria() == categoria)
                .toList();
    }

    public List<Funko> loadLista() {

        Categoria csuper = Categoria.builder()
                .id(1L)
                .tipoCategoria(Categoria.tipoCategoria.SUPERHEROES)
                .build();

        Categoria cdisney = Categoria.builder()
                .id(1L)
                .tipoCategoria(Categoria.tipoCategoria.DISNEY)
                .build();

        funkos = new ArrayList<>();
        funkos.add(Funko.builder()
                .nombre("Spiderman")
                .precio(15.99)
                .cantidad(5)
                .categoria(csuper)
                .build());

        funkos.add(Funko.builder()
                .nombre("Iron Man")
                .precio(12.99)
                .cantidad(10)
                .categoria(csuper)
                .build());

        funkos.add(Funko.builder()
                .nombre("Captain America")
                .precio(14.99)
                .cantidad(7)
                .categoria(csuper)
                .build());

        funkos.add(Funko.builder()
                .nombre("Mickey Mouse")
                .precio(10.99)
                .cantidad(15)
                .categoria(cdisney)
                .build());

        funkos.add(Funko.builder()
                .nombre("Minnie Mouse")
                .precio(12.99)
                .cantidad(8)
                .categoria(cdisney)
                .build());

        funkos.add(Funko.builder()
                .nombre("Donald Duck")
                .precio(9.99)
                .cantidad(20)
                .categoria(cdisney)
                .build());
        return funkos;
    }

}
