package com.egg.casadeelectricidad.controladores;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.egg.casadeelectricidad.entidades.Articulo;
import com.egg.casadeelectricidad.entidades.Fabrica;
import com.egg.casadeelectricidad.excepciones.MiException;
import com.egg.casadeelectricidad.servicios.ArticuloServicio;
import com.egg.casadeelectricidad.servicios.FabricaServicio;

@Controller
@RequestMapping("/articulo")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;

    @Autowired
    private FabricaServicio fabricaServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        model.addAttribute("fabricas", fabricaServicio.listarFabricas());
        model.addAttribute("fabrica", new Fabrica());
        return "articulo_form.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam(required = false) Integer nroArticulo,
            @RequestParam String nombreArticulo,
            @RequestParam String descripcionArticulo,
            @RequestParam String idFabrica,
            @RequestParam(required = false) MultipartFile archivo,
            ModelMap model) {

        try {
            UUID fabricaUUID = (idFabrica != null && !idFabrica.isEmpty()) ? UUID.fromString(idFabrica) : null;
            if (fabricaUUID == null) {
                throw new MiException("La fábrica no puede ser nula");
            }
            articuloServicio.crearArticulo(archivo, fabricaUUID, nroArticulo, nombreArticulo, descripcionArticulo);
            model.put("exito", "El artículo fue creado correctamente.");
            return "redirect:/articulo/lista";
        } catch (MiException e) {
            model.put("error", e.getMessage());
            model.put("nroArticulo", nroArticulo);
            model.put("nombreArticulo", nombreArticulo);
            model.put("descripcionArticulo", descripcionArticulo);
            model.put("fabricaSeleccionada", idFabrica);
            model.addAttribute("fabricas", fabricaServicio.listarFabricas());
            return "articulo_form.html";
        } catch (Exception e) {
            model.put("error", "Error inesperado: " + e.getMessage());
            return "articulo_form.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap model) {
        model.addAttribute("articulos", articuloServicio.listarArticulos());
        return "articulo_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable UUID id, ModelMap model) throws MiException {
        Articulo articulo = articuloServicio.getOne(id);
        model.addAttribute("articulo", articulo);
        model.addAttribute("fabricas", fabricaServicio.listarFabricas());
        return "articulo_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id,
            @RequestParam(required = false) Integer nroArticulo,
            @RequestParam String nombreArticulo,
            @RequestParam String descripcionArticulo,
            @RequestParam String idFabrica,
            @RequestParam(required = false) MultipartFile archivo,
            RedirectAttributes redirectAttrs) {
        try {
            UUID fabricaUUID = (idFabrica != null && !idFabrica.isEmpty()) ? UUID.fromString(idFabrica) : null;
            if (fabricaUUID == null) {
                throw new MiException("La fábrica no puede ser nula");
            }
            articuloServicio.modificarArticulo(archivo, id, fabricaUUID, nroArticulo, nombreArticulo,
                    descripcionArticulo);
            redirectAttrs.addFlashAttribute("exito", "El artículo fue modificado correctamente.");
            return "redirect:/articulo/lista";
        } catch (MiException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/articulo/modificar/" + id;
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error inesperado: " + e.getMessage());
            return "redirect:/articulo/modificar/" + id;
        }
    }

    @GetMapping("/imagen/{id}")
    public void mostrarImagen(@PathVariable UUID id, HttpServletResponse response) throws IOException, MiException {
        Articulo articulo = articuloServicio.getOne(id);

        if (articulo.getImagen() != null && articulo.getImagen().getContenido() != null) {
            response.setContentType("image/jpeg");
            response.getOutputStream().write(articulo.getImagen().getContenido());
            response.getOutputStream().flush();
        } else {
            response.sendRedirect("/imagenes/default.jpg");
        }
    }
}
