package com.egg.casadeelectricidad.entidades;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Genera automáticamente los métodos getters, setters, toString(), equals() y hashCode().
@NoArgsConstructor // Genera el constructor sin argumentos.
@AllArgsConstructor // Genera un constructor con todos los atributos.
@Builder // Permite crear objetos con un patrón de construcción más limpio y legible
public class Fabrica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFabrica;

    @Column(unique = true, nullable = false)
    String nombreFabrica;

}
