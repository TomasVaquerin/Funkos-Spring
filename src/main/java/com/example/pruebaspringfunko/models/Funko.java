package com.example.pruebaspringfunko.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Funko {
    private static Long nextId = 1L;
    @Id
    @Builder.Default
    private Long id = nextId++;
    @NotEmpty(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 30, message = "El nombre tiene que tener entre 2 y 30 caracteres")
    private String nombre;
    @NotNull(message = "El precio no puede estar vacio")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor a cero")
    @Positive(message = "El precio no puede ser negativo")
    private Double precio;
    private Integer cantidad;
    @Builder.Default
    private String imagen = null;
    private Categoria categoria;
    @Builder.Default
    private LocalDateTime fechaCreated = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime fechaUpdated = LocalDateTime.now();
}
