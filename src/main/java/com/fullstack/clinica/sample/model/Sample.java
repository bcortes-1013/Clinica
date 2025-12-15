package com.fullstack.clinica.sample.model;

import jakarta.persistence.*; // JPA: mapeo objeto–relacional (entidades, columnas, etc.)
import jakarta.validation.constraints.*; // Bean Validation (validaciones en los atributos)
import lombok.Data; // Lombok: genera getters, setters, toString, equals, hashCode

@Data // Lombok → genera automáticamente getters/setters/toString
@Entity // Indica que esta clase se mapea a una tabla en la BD
@Table(name = "SAMPLE") // Nombre explícito de la tabla en Oracle
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código no puede ser nulo")
    @Size(min = 5, max = 50, message = "El código debe tener entre 5 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String code;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 100, message = "La descripción debe tener entre 5 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String description;

    // Snapshot del técnico que tomó la muestra
    @Column(name = "technician", nullable = false, length = 100)
    private String technician;

    // Snapshot del labotatorio asociado
    @Column(name = "laboratory", nullable = false, length = 100)
    private String laboratory;
}
