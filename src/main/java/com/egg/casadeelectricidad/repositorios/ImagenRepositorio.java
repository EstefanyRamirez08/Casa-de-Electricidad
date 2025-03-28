package com.egg.casadeelectricidad.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egg.casadeelectricidad.entidades.Imagen;

public interface ImagenRepositorio  extends JpaRepository<Imagen, UUID> {
    
}
