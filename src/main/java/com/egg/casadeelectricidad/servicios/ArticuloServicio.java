package com.egg.casadeelectricidad.servicios;

import java.util.List;
//import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.casadeelectricidad.entidades.Articulo;
import com.egg.casadeelectricidad.entidades.Imagen;
import com.egg.casadeelectricidad.entidades.Fabrica;
import com.egg.casadeelectricidad.excepciones.MiException;
import com.egg.casadeelectricidad.repositorios.ArticuloRepositorio;
import com.egg.casadeelectricidad.repositorios.FabricaRepositorio;

@Service
public class ArticuloServicio {

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Autowired
    private FabricaRepositorio fabricaRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearArticulo(MultipartFile archivo, UUID idFabrica, Integer nroArticulo, String nombreArticulo,
            String descripcionArticulo) throws MiException {

        validar(nroArticulo, nombreArticulo, descripcionArticulo, idFabrica);

        Fabrica fabrica = fabricaRepositorio.findById(idFabrica)
                .orElseThrow(() -> new MiException("La fábrica no existe"));

        Articulo articulo = Articulo.builder()
                .nroArticulo(nroArticulo)
                .nombreArticulo(nombreArticulo)
                .descripcionArticulo(descripcionArticulo)
                .fabrica(fabrica)
                .build();

                if (archivo != null && !archivo.isEmpty()) {
                    Imagen imagen = imagenServicio.guardar(archivo);
                    articulo.setImagen(imagen);
                }   

        articuloRepositorio.save(articulo);
    }

    @Transactional(readOnly = true)
    public List<Articulo> listarArticulos() {
        List<Articulo> articulos = articuloRepositorio.findAll();
        System.out.println("Cantidad de artículos encontrados: " + articulos.size());
        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
        return articulos;
    }

    @Transactional
    public void modificarArticulo(MultipartFile archivo, UUID idArticulo, UUID idFabrica, 
                                   Integer nroArticulo, String nombreArticulo, String descripcionArticulo) 
                                   throws MiException {
        validar(nroArticulo, nombreArticulo, descripcionArticulo, idFabrica);
    
        Articulo articulo = articuloRepositorio.findById(idArticulo)
                .orElseThrow(() -> new MiException("El artículo no existe"));
    
        Fabrica fabrica = fabricaRepositorio.findById(idFabrica)
                .orElseThrow(() -> new MiException("La fábrica no existe"));
    
        articulo.setNroArticulo(nroArticulo);
        articulo.setNombreArticulo(nombreArticulo);
        articulo.setDescripcionArticulo(descripcionArticulo);
        articulo.setFabrica(fabrica);
    
        if (archivo != null && !archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            articulo.setImagen(imagen);
        }
    
        articuloRepositorio.save(articulo);
    }
    

    @Transactional(readOnly = true)
    public Articulo getOne(UUID idArticulo) throws MiException {
        return articuloRepositorio.findById(idArticulo)
                .orElseThrow(() -> new MiException("El artículo no existe"));
    }

    private void validar(Integer nroArticulo, String nombreArticulo,
            String descripcionArticulo, UUID idFabrica) throws MiException {

        if (nroArticulo != null && nroArticulo <= 0) {
            throw new MiException("El número de artículo debe ser mayor que cero");
        }
        if (nombreArticulo == null || nombreArticulo.trim().isEmpty()) {
            throw new MiException("El nombre del artículo no puede ser nulo o vacío");
        }
        if (descripcionArticulo == null || descripcionArticulo.trim().isEmpty()) {
            throw new MiException("La descripción no puede ser nula o vacía");
        }
        if (idFabrica == null) {
            throw new MiException("El ID de la fábrica no puede ser nulo");
        }
    }
}