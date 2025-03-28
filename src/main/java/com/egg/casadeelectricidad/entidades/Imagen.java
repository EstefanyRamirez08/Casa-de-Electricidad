package com.egg.casadeelectricidad.entidades;

import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Genera automáticamente los métodos getters, setters, toString(), equals() y hashCode().
@NoArgsConstructor // Genera el constructor sin argumentos.
@AllArgsConstructor // Genera un constructor con todos los atributos.
@Builder // Permite crear objetos con un patrón de construcción más limpio y legible
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String mime;

    private String nombre;

    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;
    
}
