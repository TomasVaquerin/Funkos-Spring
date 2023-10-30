package com.example.pruebaspringfunko.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FunkoRepositoryImplTest {
    FunkoRepository funkoRepository;

    @BeforeEach
    void setUp(){
        funkoRepository = new FunkoRepositoryImpl();
    }

}