package com.example.funko.Servicies;

import com.example.funko.models.Funko;
import com.example.funko.repository.FunkoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FunkoService {

    private final FunkoRepository funkoRepository;

    @Autowired
    public FunkoService(FunkoRepository funkoRepository) {
        this.funkoRepository = funkoRepository;
    }

    public List<Funko> getAllFunkos() {
        return funkoRepository.getAllFunkos();
    }

    public Optional<Funko> getFunkoById(Long id) {
        return funkoRepository.getFunkoById(id);
    }

    public Optional<Funko> createFunko(Funko funko) {
        return funkoRepository.createFunko(funko);
    }

    public Optional<Funko> updateFunko(Long id, Funko updatedFunko) {
        return funkoRepository.updateFunko(id, updatedFunko);
    }

    public Optional<Funko> deleteFunko(Long id) {
        return funkoRepository.deleteFunko(id);
    }

    public Optional<Funko> partiallyUpdateFunko(Long id, Funko partialFunko) {
        return funkoRepository.partiallyUpdateFunko(id,partialFunko);
    }

    //mapers
}
