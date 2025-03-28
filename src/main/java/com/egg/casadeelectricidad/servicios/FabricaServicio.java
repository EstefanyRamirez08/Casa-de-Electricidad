package com.egg.casadeelectricidad.servicios;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.casadeelectricidad.entidades.Fabrica;
import com.egg.casadeelectricidad.excepciones.MiException;
import com.egg.casadeelectricidad.repositorios.FabricaRepositorio;

@Service
public class FabricaServicio {

    @Autowired
    private FabricaRepositorio fabricaRepositorio;

    @Transactional
    public void crearFabrica(String nombreFabrica) throws MiException {
        if (nombreFabrica == null || nombreFabrica.trim().isEmpty()) {
            throw new MiException("El nombre de la fábrica no puede ser nulo o vacío");
        }

        Fabrica fabrica = Fabrica.builder()
                .nombreFabrica(nombreFabrica)
                .build();

        fabricaRepositorio.save(fabrica);
    }

    @Transactional
    public List<Fabrica> listarFabricas() {
        List<Fabrica> fabricas = fabricaRepositorio.findAll();
        System.out.println("Cantidad de fábricas encontradas: " + fabricas.size());
        return fabricas;
    }

    @Transactional
    public void modificarFabrica(UUID idFabrica, String nombreFabrica) throws MiException {

        Optional<Fabrica> respuesta = fabricaRepositorio.findById(idFabrica);

        if (respuesta.isPresent()) {
            Fabrica fabrica = respuesta.get();
            fabrica.setNombreFabrica(nombreFabrica);
            fabricaRepositorio.save(fabrica);
        } else {
            throw new MiException("no se encontro una fabrica con el id especificado");
        }
    }

    @Transactional
    public void eliminar(UUID idFabrica) throws MiException {
        Optional<Fabrica> fabricaOpt = fabricaRepositorio.findById(idFabrica);
        if (fabricaOpt.isPresent()) {
            fabricaRepositorio.delete(fabricaOpt.get());

        } else {
            throw new MiException("no se encontro una fabrica con el id especificado");
        }
    }

    @Transactional(readOnly = true)
    public Fabrica getOne(UUID idFabrica) {
        return fabricaRepositorio.findById(idFabrica).orElse(null);
    }

   

}
