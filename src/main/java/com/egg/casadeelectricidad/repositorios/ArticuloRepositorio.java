package com.egg.casadeelectricidad.repositorios;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.casadeelectricidad.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, UUID> {

    @Query("SELECT a FROM Articulo a WHERE a.fabrica.idFabrica = :idFabrica")
List<Articulo> buscarPorFabrica(@Param("idFabrica") UUID idFabrica);

}
