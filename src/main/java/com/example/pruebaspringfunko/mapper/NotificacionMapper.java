package com.example.pruebaspringfunko.mapper;

import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.websockets.FunkoNotificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificacionMapper {
    public FunkoNotificacion convertirFunkoaFunkoNotificacion(Funko funko){
        return new FunkoNotificacion(
                funko.getId(), funko.getNombre(), funko.getPrecio(), funko.getCantidad(), funko.getImagen(), funko.getCategoria().getName(), funko.getFecha_created().toString(), funko.getFecha_updated().toString()
        );
    }
}
