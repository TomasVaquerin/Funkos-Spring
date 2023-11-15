package com.example.pruebaspringfunko.websockets;

import java.time.LocalDateTime;

public record FunkoNotificacion(
        Long id,
        String nombre,
        Double precio,
        Integer cantidad,
        String imagen,
        String categoria,
        String fecha_created,
        String fecha_updated
) {
}
