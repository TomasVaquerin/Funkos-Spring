package com.example.pruebaspringfunko.services;

import com.example.pruebaspringfunko.config.WebSocketConfig;
import com.example.pruebaspringfunko.config.WebSocketHandler;
import com.example.pruebaspringfunko.exceptions.BadRequesException;
import com.example.pruebaspringfunko.exceptions.NotFoundException;
import com.example.pruebaspringfunko.mapper.FunkoMapper;
import com.example.pruebaspringfunko.mapper.NotificacionMapper;
import com.example.pruebaspringfunko.models.Categoria;
import com.example.pruebaspringfunko.models.Funko;
import com.example.pruebaspringfunko.models.FunkoDTOCreUpd;
import com.example.pruebaspringfunko.repositories.CategoriaRepository;
import com.example.pruebaspringfunko.repositories.NewFunkoRepository;
import com.example.pruebaspringfunko.websockets.FunkoNotificacion;
import com.example.pruebaspringfunko.websockets.Notificacion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"funks"})
public class FunkoServiceImpl implements FunkoService{
    private final NewFunkoRepository funkoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FunkoMapper mapper;
    private final NotificacionMapper notificationMapper;
    private final WebSocketConfig webSocketConfig;
    private final ObjectMapper objectMapper;
    private WebSocketHandler webSocketHandler;

    @Autowired
    public FunkoServiceImpl(NewFunkoRepository funkoRepository, CategoriaRepository categoriaRepository, FunkoMapper mapper, NotificacionMapper notificationMapper, WebSocketConfig webSocketConfig) {
        this.funkoRepository = funkoRepository;
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
        this.notificationMapper = notificationMapper;
        this.webSocketConfig = webSocketConfig;
        webSocketHandler = webSocketConfig.webSocketFunkosHandler();
        objectMapper = new ObjectMapper();
    }

    @Override
    @CachePut
    public Funko save(FunkoDTOCreUpd funkoDTO) {
        var categoria = checkCategoria(funkoDTO.getCategoria());
        Funko funko = mapper.convertirDTOaFunko(funkoDTO, categoria);
        onChange(Notificacion.Tipo.CREATE, funko);
        return funkoRepository.save(funko);
    }

    @Override
    @CachePut
    public Funko update(Long id, FunkoDTOCreUpd funkoDTO) {
        var categoria = checkCategoria(funkoDTO.getCategoria());
        Funko existingFunko = funkoRepository.findById(id).orElse(null);
        Funko updatedFunkoDTO = mapper.convertirDTOaFunko(funkoDTO, categoria);

        if (existingFunko != null) {
            existingFunko.setNombre(updatedFunkoDTO.getNombre());
            existingFunko.setPrecio(updatedFunkoDTO.getPrecio());
            existingFunko.setCantidad(updatedFunkoDTO.getCantidad());
            existingFunko.setCategoria(updatedFunkoDTO.getCategoria());
            onChange(Notificacion.Tipo.UPDATE, existingFunko);
            return funkoRepository.save(existingFunko);
        } else {
            throw new NotFoundException("El funko con id " + id + " no existe");
        }
    }

    @Override
    public Page<Funko> findAll(Optional<String> nombre, Optional<Double> precio, Optional<Integer> cantidad, Optional<String> categoria, Pageable pageable) {
        Specification<Funko> specNombre = (root, query, criteriaBuilder) ->
                nombre.map(n -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + n.toLowerCase() + "%"))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> specPrecio = (root, query, criteriaBuilder) ->
                precio.map(p -> criteriaBuilder.lessThanOrEqualTo(root.get("precio"), p))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> specCantidad = (root, query, criteriaBuilder) ->
                cantidad.map(c -> criteriaBuilder.lessThanOrEqualTo(root.get("cantidad"), c))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> specCategoria = (root, query, criteriaBuilder) ->
                categoria.map(c -> {
                    Join<Funko, Categoria> categoriaJoin = root.join("categoria");
                    return criteriaBuilder.like(criteriaBuilder.lower(categoriaJoin.get("name")), "%" + c.toLowerCase() + "%"); // Buscamos por nombre
                }).orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> criterios = Specification.where(specNombre)
                .and(specPrecio).and(specCantidad).and(specCategoria);
        return funkoRepository.findAll(criterios, pageable);
    }

    @Override
    @Cacheable
    public Funko findById(Long id) {
        Funko existingFunko = funkoRepository.findById(id).orElse(null);
        if (existingFunko != null){
            return existingFunko;
        }else {
            throw new NotFoundException("El funko con id " + id + " no existe");
        }
    }

    @Override
    @CacheEvict
    public void deleteById(Long id) {
        Funko existingFunko = funkoRepository.findById(id).orElse(null);
        if (existingFunko != null){
            onChange(Notificacion.Tipo.DELETE, existingFunko);
           funkoRepository.deleteById(id);
        }else {
            throw new NotFoundException("El funko con id " + id + " no existe");
        }
    }

    private Categoria checkCategoria(String nombreCategoria) {
        var categoria = categoriaRepository.findByNameEqualsIgnoreCase(nombreCategoria);
        if (categoria.isEmpty()) {
            throw new BadRequesException("La categoría " + nombreCategoria + " no existe o está borrada");
        }
        return categoria.get();
    }

    void onChange(Notificacion.Tipo tipo, Funko data) {

        if (webSocketHandler == null) {
            webSocketHandler = this.webSocketConfig.webSocketFunkosHandler();
        }

        try {
            Notificacion<FunkoNotificacion> notificacion = new Notificacion<>(
                    "Funkos",
                    tipo,
                    notificationMapper.convertirFunkoaFunkoNotificacion(data),
                    LocalDateTime.now().toString()
            );

            String json = objectMapper.writeValueAsString((notificacion));

            Thread senderThread = new Thread(() -> {
                try {
                    webSocketHandler.sendMessage(json);
                } catch (Exception e) {
                }
            });
            senderThread.start();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
