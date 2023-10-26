package com.example.pruebaspringfunko.repositories;

import com.example.pruebaspringfunko.models.Funko;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class FunkoRepositoryImpl implements FunkoRepository{
    List<Funko> funkos;

    public FunkoRepositoryImpl() {
        //funkos = insertarDatos();
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
            if (funko.getId() == id) {
                return Optional.of(funko);
            }
        }
        return Optional.empty();
    }


    @Override
    public void deleteById(Long id) {
        funkos.removeIf(funko -> funko.getId() == id);
    }

    public List<Funko> getFunkosPorCategoria(Funko.Categoria categoria) {
        return funkos.stream()
                .filter(funko -> funko.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    /*public List<Funko> insertarDatos(){
        List<Funko> funkos = new ArrayList<>();
        String csvFile = Paths.get("").toAbsolutePath() + File.separator + "data" + File.separator + "funkos.csv";
        String line;

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            br.readLine();
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                Funko funko = new Funko(
                        UUID.fromString(data[0].substring(0,35)),
                        data[1],
                        Funko.Categoria.valueOf(data[2]),
                        Double.parseDouble(data[3]));
                funkos.add(funko);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return funkos;
    }*/
    public List<Funko> loadLista(){
        funkos = new ArrayList<>();
        funkos.add(new Funko("Spiderman", 15.99, 5, Funko.Categoria.MARVEL));
        funkos.add(new Funko("Daisy", 10.99, 15, Funko.Categoria.DISNEY));
        funkos.add(new Funko("Akaza", 20.99, 10, Funko.Categoria.ANIME));
        funkos.add(new Funko("Tomas Shelby", 30.99, 2, Funko.Categoria.OTROS));
        return funkos;
    }

}
