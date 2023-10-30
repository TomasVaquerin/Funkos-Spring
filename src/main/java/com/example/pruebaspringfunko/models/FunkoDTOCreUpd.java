package com.example.pruebaspringfunko.models;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FunkoDTOCreUpd {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 30, message = "El nombre tiene que tener entre 2 y 30 caracteres")
    String nombre;
    @NotNull(message = "El precio no puede estar vacio")
    @DecimalMin(value = "0.1", message = "El precio no puede ser negativo o 0")
    Double precio;
    @NotNull(message = "La cantidad no puede estar vacia")
    @Min(value = 1, message = "Minimo tiene que haber 1 producto")
    @Positive(message = "La cantidad no puede ser negativa")
    Integer cantidad;
    @NotNull(message = "La categoria no puede estar vacia")
    Funko categoria;
}
