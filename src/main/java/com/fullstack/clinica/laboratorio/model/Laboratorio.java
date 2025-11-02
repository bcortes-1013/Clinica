package com.fullstack.clinica.laboratorio.model;

import jakarta.persistence.*; // JPA: mapeo objeto–relacional (entidades, columnas, etc.)
import jakarta.validation.constraints.*; // Semana 2 → Bean Validation (validaciones en los atributos)
import lombok.Data; // Lombok: genera getters, setters, toString, equals, hashCode

/**
 * ===============================================================
 * 📘 Clase: Laboratorio
 * ---------------------------------------------------------------
 * Entidad principal del sistema de gestión de laboratorios.
 * Cada objeto de esta clase representa un registro en la tabla LABORATORIO
 * dentro de la base de datos Oracle.
 * laboratorio
 * 🔹 Semana 1:
 * - Modelo simple con atributos básicos y persistencia con JPA.
 * 🔹 Semana 2:
 * - Se agregan validaciones de datos (Bean Validation)
 * - Se mejoran los comentarios para documentación profesional.
 * - Preparado para integrarse con manejo de excepciones globales.
 * ===============================================================
 */
@Data // Lombok → genera automáticamente getters/setters/toString
@Entity // Indica que esta clase se mapea a una tabla en la BD
@Table(name = "LABORATORIO") // Nombre explícito de la tabla en Oracle
public class Laboratorio {

    // ============================================================
    // 🔸 Semana 1: Identificador primario
    // ============================================================
    /**
     * Identificador único del laboratorio.
     * 
     * @Id → marca el atributo como clave primaria.
     * @GeneratedValue → indica que se genera automáticamente (autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // 🔸 Semana 2: Validaciones Bean Validation
    // ============================================================

    /**
     * Nombre del laboratorio.
     * Debe tener entre 3 y 100 caracteres y no puede ser nulo o vacío.
     * 
     * 🔍 Validaciones:
     * 
     * @NotBlank → no permite valores nulos o vacios.
     * @Size → controla longitud mínima y máxima del texto.
     */
    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Descripción del laboratorio.
     * También obligatorio y con longitud mínima.
     */
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 3, max = 100, message = "La descripción debe tener entre 10 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String descripcion;

    /**
     * Tipo de laboratorio.
     * Ejemplo: "Hematología", "Bioquímica", "Microbiología", etc.
     */
    @NotNull(message = "El tipo es obligatorio")
    @Size(min = 5, max = 50, message = "El tipo debe tener entre 5 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String tipo;


    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Max(value = 500, message = "La capacidad máxima es 500")
    @Column(nullable = false)
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser ACTIVO o INACTIVO")
    @Column(nullable = false, length = 20)
    private String estado;

    @NotBlank(message = "El tipo de análisis es obligatorio")
    @Size(min = 3, max = 100, message = "El tipo de análisis debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String tipoAnalisis;

    // ============================================================
    // 🧠 Notas Semana 2:
    // ------------------------------------------------------------
    // - Estas validaciones se aplican automáticamente cuando
    // se recibe un objeto Laboratorio en el controlador anotado con @Valid.
    // - Si falla una validación, Spring lanza una excepción
    // MethodArgumentNotValidException, que capturaremos
    // en GlobalExceptionHandler (Paso 4).
    // ============================================================
}
