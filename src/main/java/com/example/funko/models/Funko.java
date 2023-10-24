package com.example.funko.models;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Funko {
    private Long id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String imagen;
    private String categoria;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
}
