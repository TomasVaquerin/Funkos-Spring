package com.example.pruebaspringfunko.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Funko {
    private static Long nextId = 1L;
    private Long id;
    @NotEmpty(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 30, message = "El nombre tiene que tener entre 2 y 30 caracteres")
    private String nombre;
    @NotNull(message = "El precio no puede estar vacio")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor a cero")
    @Positive(message = "El precio no puede ser negativo")
    private Double precio;
    private Integer cantidad;
    private String imagen;
    private Categoria categoria;
    private LocalDateTime fecha_created;
    private LocalDateTime fecha_updated;



    public enum Categoria{
        MARVEL, DISNEY, ANIME, OTROS
    }

    public Funko(String nombre, Double precio, Integer cantidad, Categoria categoria) {
        this.id = nextId++;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = null;
        this.categoria = categoria;
        this.fecha_created = LocalDateTime.now();
        this.fecha_updated = LocalDateTime.now();
    }
}
