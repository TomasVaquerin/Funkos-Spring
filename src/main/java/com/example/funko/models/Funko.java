package com.example.funko.models;


import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class Funko {
    private Long id;
    private String nombre;
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    private double precio;
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    private int cantidad;
    private String imagen;
    private String categoria;
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
}
