package com.example.pruebaspringfunko.mapper;

import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;

public class FunkoMapper {
    public static Funko convertirDTOaFunko(FunkoDTOCreUpd dto) {
        return Funko.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .cantidad(dto.getCantidad())
                .categoria(dto.getCategoria())
                .build();
    }

    public static FunkoDTOCreUpd convertFunkDTO(Funko funko) {
        return FunkoDTOCreUpd.builder()
                .nombre(funko.getNombre())
                .precio(funko.getPrecio())
                .cantidad(funko.getCantidad())
                .categoria(funko.getCategoria())
                .build();
    }
}
