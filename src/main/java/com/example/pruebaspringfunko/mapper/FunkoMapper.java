package com.example.pruebaspringfunko.mapper;

import com.example.pruebaspringfunko.models.Categoria;
import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;

import java.time.LocalDateTime;

public class FunkoMapper {
    public static Funko convertirDTOaFunko(FunkoDTOCreUpd dto, Categoria categoria) {
        Funko funko = Funko.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .cantidad(dto.getCantidad())
                .categoria(categoria)
                .fecha_created(LocalDateTime.now())
                .fecha_updated(LocalDateTime.now())
                .build();

        return funko;
    }

    public static FunkoDTOCreUpd convertirFunkoaDTO(Funko funko) {
        FunkoDTOCreUpd dto = new FunkoDTOCreUpd();
        dto.setNombre(funko.getNombre());
        dto.setPrecio(funko.getPrecio());
        dto.setCantidad(funko.getCantidad());
        dto.setCategoria(funko.getCategoria().getName());
        return dto;
    }

}
