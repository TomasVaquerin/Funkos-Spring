package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Funko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface NewFunkoRepository extends JpaRepository<Funko, Long>, JpaSpecificationExecutor<Funko> {

}
