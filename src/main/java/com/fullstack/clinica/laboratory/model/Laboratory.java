package com.fullstack.clinica.laboratory.model;

import jakarta.persistence.*; // JPA: mapeo objeto–relacional (entidades, columnas, etc.)
import jakarta.validation.constraints.*; // Bean Validation (validaciones en los atributos)
import lombok.Data; // Lombok: genera getters, setters, toString, equals, hashCode

@Data // Lombok → genera automáticamente getters/setters/toString
@Entity // Indica que esta clase se mapea a una tabla en la BD
@Table(name = "LABORATORY") // Nombre explícito de la tabla en Oracle
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 100, message = "La descripción debe tener entre 5 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String description;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
    @Column(nullable = false, length = 20)
    private String state;
}
