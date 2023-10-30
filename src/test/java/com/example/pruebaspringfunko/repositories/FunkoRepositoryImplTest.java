package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Funko;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunkoRepositoryImplTest {
    FunkoRepository funkoRepository;

    @BeforeEach
    void setUp(){
        funkoRepository = new FunkoRepositoryImpl();
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
        var listaFunkos = funkoRepository.findAll();
        assertAll(
                () -> assertNotNull(listaFunkos),
                () -> assertEquals(4, listaFunkos.size())
        );
    }

    @Test
    void findById() {
        var funko = funkoRepository.findById(1L);

        assertAll(
                () -> assertNotNull(funko),
                () -> assertEquals("Spiderman", funko.get().getNombre()),
                () -> assertEquals(15.99, funko.get().getPrecio()),
                () -> assertEquals(5, funko.get().getCantidad()),
                () -> assertEquals(Funko.Categoria.MARVEL, funko.get().getCategoria())
        );
    }

    @Test
    void findByIdNotFound() {
        var funko = funkoRepository.findById(99L);

        assertAll(
                () -> assertNotNull(funko),
                () -> assertFalse(funko.isPresent())
        );
    }

    @Test
    void deleteById() {
    }

    @Test
    void getFunkosPorCategoria() {
    }

    @Test
    void loadLista() {
    }
}