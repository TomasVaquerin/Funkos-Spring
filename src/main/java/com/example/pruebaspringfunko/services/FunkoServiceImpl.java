package com.example.pruebaspringfunko.services;

import com.example.pruebaspringfunko.exceptions.NotFoundException;
import com.example.pruebaspringfunko.mapper.FunkoMapper;
import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;
import com.example.pruebaspringfunko.repositories.FunkoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"funks"})
public class FunkoServiceImpl implements FunkoService{
    private final FunkoRepositoryImpl funkoRepository;

    @Autowired
    public FunkoServiceImpl(FunkoRepositoryImpl funkoRepository) {
        this.funkoRepository = funkoRepository;
    }

    @Override
    @CachePut
    public Funko save(FunkoDTOCreUpd funkoDTO) {
        Funko funko = FunkoMapper.convertirDTOaFunko(funkoDTO);
        return funkoRepository.save(funko);
    }

    private String idNoExiste(Long id) {
        return "El funko con id " + id + " no existe";
    }

    @Override
    @CachePut
    public Funko update(Long id, FunkoDTOCreUpd funkoDTO) {
        Funko existingFunko = funkoRepository.findById(id).orElse(null);
        Funko updatedFunkoDTO = FunkoMapper.convertirDTOaFunko(funkoDTO);

        if (existingFunko != null) {
            existingFunko.setNombre(updatedFunkoDTO.getNombre());
            existingFunko.setPrecio(updatedFunkoDTO.getPrecio());
            existingFunko.setCantidad(updatedFunkoDTO.getCantidad());
            existingFunko.setCategoria(updatedFunkoDTO.getCategoria());
            return funkoRepository.update(existingFunko);
        } else {
            throw new NotFoundException(idNoExiste(id));
        }
    }

    @Override
    public List<Funko> findAll() {
        return funkoRepository.findAll();
    }

    @Override
    @Cacheable
    public Funko findById(Long id) {
        Funko existingFunko = funkoRepository.findById(id).orElse(null);
        if (existingFunko != null){
            return existingFunko;
        }else {
            throw new NotFoundException(idNoExiste(id));
        }
    }

    @Override
    @CacheEvict
    public void deleteById(Long id) {
        Funko existingFunko = funkoRepository.findById(id).orElse(null);
        if (existingFunko != null){
           funkoRepository.deleteById(id);
        }else {
            throw new NotFoundException(idNoExiste(id));
        }
    }

    public List<Funko> getFunkosPorCategoria(Funko.Categoria categoria){
        return funkoRepository.getFunkosPorCategoria(categoria);
    }
}
