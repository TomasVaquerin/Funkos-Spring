package com.example.funko.models;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class Funko {
    private Long id;
    private String nombre;
    @Min()
    private double precio;
    private int cantidad;
    private String imagen;
    private String categoria;
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
}
