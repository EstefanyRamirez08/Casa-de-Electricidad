package com.egg.casadeelectricidad.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egg.casadeelectricidad.entidades.Fabrica;

public interface FabricaRepositorio extends JpaRepository<Fabrica, UUID> {

    

    
}
