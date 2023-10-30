package com.example.pruebaspringfunko.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {

    @Id
    private Long id;

    @Builder.Default
    private LocalDateTime fechaCreated = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime fechaUpdated = LocalDateTime.now();

    private tipoCategoria tipoCategoria;

    public enum tipoCategoria{
        SERIE, DISNEY, SUPERHEROES, PELICULA, OTROS
    }
}
