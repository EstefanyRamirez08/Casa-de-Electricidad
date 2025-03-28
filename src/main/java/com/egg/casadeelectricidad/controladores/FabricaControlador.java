package com.egg.casadeelectricidad.controladores;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.egg.casadeelectricidad.excepciones.MiException;
import com.egg.casadeelectricidad.servicios.FabricaServicio;

@Controller
@RequestMapping("/fabrica")
public class FabricaControlador {

    @Autowired
    private FabricaServicio fabricaServicio;

    private static final Logger logger = LoggerFactory.getLogger(FabricaControlador.class);

    // Vista para formulario de registro
    @GetMapping("/registrar")
    public String registrar() {
        return "fabrica_form.html";
    }

    // Procesa el registro de fábrica
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre de la fábrica no puede estar vacío.");
                return "redirect:/fabrica/registrar";
            }
            fabricaServicio.crearFabrica(nombre.trim());
            redirectAttributes.addFlashAttribute("exito", "La fábrica fue creada correctamente.");
            return "redirect:/fabrica/lista";
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/fabrica/registrar";
        } catch (Exception e) {
            logger.error("Error inesperado: ", e);
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado al registrar la fábrica.");
            return "redirect:/fabrica/registrar";
        }
    }

    // Listar las fábricas
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        logger.info("Entrando al método listar() para mostrar fábricas.");
        modelo.addAttribute("fabricas", fabricaServicio.listarFabricas());
        return "fabrica_list.html";
    }

    // Vista para modificar fábrica
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap model) {
        model.put("fabrica", fabricaServicio.getOne(id));
        return "fabrica_modificar.html";
    }

    // Procesa la modificación de la fábrica
    @PostMapping("/modificar/{id}")
    public String modificarFabrica(@PathVariable String id, @RequestParam String nombre,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre de la fábrica no puede estar vacío.");
                return "redirect:/fabrica/modificar/" + id;
            }

            UUID idFabrica = UUID.fromString(id);
            fabricaServicio.modificarFabrica(idFabrica, nombre.trim());
            redirectAttributes.addFlashAttribute("exito", "Fábrica actualizada con éxito.");
            return "redirect:/fabrica/lista";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "ID de fábrica no válido.");
            return "redirect:/fabrica/modificar/" + id;
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/fabrica/modificar/" + id;
        } catch (Exception e) {
            logger.error("Error inesperado: ", e);
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado al modificar la fábrica.");
            return "redirect:/fabrica/modificar/" + id;
        }
    }
}
