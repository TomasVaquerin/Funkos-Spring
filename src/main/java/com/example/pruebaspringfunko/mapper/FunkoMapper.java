package com.example.pruebaspringfunko.mapper;

import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;

public class FunkoMapper {
    public static Funko convertirDTOaFunko(FunkoDTOCreUpd dto) {
        Funko funko = new Funko(
                dto.getNombre(),
                dto.getPrecio(),
                dto.getCantidad(),
                dto.getCategoria()

        );
        return funko;
    }

    public static FunkoDTOCreUpd convertirFunkoaDTO(Funko funko) {
        FunkoDTOCreUpd dto = new FunkoDTOCreUpd();
        dto.setNombre(funko.getNombre());
        dto.setPrecio(funko.getPrecio());
        dto.setCantidad(funko.getCantidad());
        dto.setCategoria(funko.getCategoria());
        return dto;
    }

}
