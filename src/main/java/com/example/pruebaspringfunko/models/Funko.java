package com.example.pruebaspringfunko.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Funko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private Double precio;
    @Column
    private Integer cantidad;
    @Column
    private String imagen;
    @ManyToOne
    @JoinColumn
    private Categoria categoria;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime fecha_created = LocalDateTime.now();
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime fecha_updated = LocalDateTime.now();



    /*public enum Categoria{
        MARVEL, DISNEY, ANIME, OTROS
    }*/

    /*public Funko(String nombre, Double precio, Integer cantidad, Categoria categoria) {
        this.id = nextId++;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = null;
        this.categoria = categoria;
        this.fecha_created = LocalDateTime.now();
        this.fecha_updated = LocalDateTime.now();
    }*/
}
