package com.egg.casadeelectricidad.entidades;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Genera automáticamente los métodos getters, setters, toString(), equals() y
      // hashCode().
@NoArgsConstructor // Genera el constructor sin argumentos.
@AllArgsConstructor // Genera un constructor con todos los atributos.
@Builder // Permite crear objetos con un patrón de construcción más limpio y legible
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idArticulo;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    private Integer nroArticulo;

    @Column(unique = true, nullable = false)
    private String nombreArticulo;

    @Column( nullable = false)
    private String descripcionArticulo;

    @ManyToOne
    @JoinColumn(name = "id_fabrica", nullable = false)
    private Fabrica fabrica;

    @OneToOne
    private Imagen imagen;

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    // Se ejecuta antes de insertar un nuevo Articulo en la BD
    @PrePersist
    private void asignarNumeroArticulo() {
        if (this.nroArticulo == null) {
            // Calcular el siguiente número basado en los registros ya guardados
            this.nroArticulo = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        }
    }
}