package com.fullstack.clinica.laboratorio.controller;

import com.fullstack.clinica.laboratorio.model.Laboratorio;
import com.fullstack.clinica.laboratorio.service.LaboratorioService;

import jakarta.validation.Valid; // Semana 2 → para activar Bean Validation
import lombok.extern.slf4j.Slf4j; // Semana 2 → logging profesional
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ===============================================================
 * 📘 Clase: LaboratorioController
 * ---------------------------------------------------------------
 * Capa que expone los endpoints REST a los clientes (frontend o Postman).
 *
 * 🔹 Semana 1:
 * - CRUD básico.
 * 🔹 Semana 2:
 * - Agrega validaciones con @Valid.
 * - Usa ResponseEntity para devolver códigos HTTP correctos.
 * - Integra logs (@Slf4j).
 * - Separa responsabilidades y prepara la API para manejo global de errores.
 * ===============================================================
 */
@Slf4j
@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    private final LaboratorioService service;

    // Inyección del servicio
    public LaboratorioController(LaboratorioService service) {
        this.service = service;
    }

    // ============================================================
    // 🔸 Semana 1 → Listar todos los laboratorios
    // ============================================================
    /**
     * GET /api/laboratorios
     * Retorna la lista completa de laboratorios.
     */
    @GetMapping
    public ResponseEntity<List<Laboratorio>> listar() {
        log.info("📚 [GET] Solicitando listado completo de laboratorios");
        List<Laboratorio> laboratorios = service.findAll();
        return ResponseEntity.ok(laboratorios);
    }

    // ============================================================
    // 🔸 Semana 2 → Buscar laboratorio por ID con control de errores
    // ============================================================
    /**
     * GET /api/laboratorios/{id}
     * Busca un laboratorio por su ID.
     * Si no existe, se lanza ResourceNotFoundException (capturada globalmente).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Laboratorio> obtenerPorId(@PathVariable Long id) {
        log.info("🔍 [GET] Buscando laboratorio con ID: {}", id);
        Laboratorio laboratorio = service.findById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(laboratorio);
    }

    // ============================================================
    // 🔸 Semana 2 → Crear laboratorio con validaciones
    // ============================================================
    /**
     * POST /api/laboratorios
     * Crea un nuevo laboratorio validando los datos (Bean Validation).
     * 
     * 🔍 Ejemplo JSON:
     * {
     *  "nombre": "Laboratorio Central de Hematología",
     *  "descripcion": "Realiza análisis de sangre rutinarios y especializados",
     *  "tipo": "Hematología",
     * }
     */
    @PostMapping
    public ResponseEntity<Laboratorio> crear(@Valid @RequestBody Laboratorio laboratorio) {
        log.info("📝 [POST] Creando laboratorio: {} - {}", laboratorio.getNombre(), laboratorio.getTipo());
        Laboratorio nuevo = service.save(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ============================================================
    // 🔸 Semana 2 → Actualizar laboratorio con @Valid y manejo de errores
    // ============================================================
    /**
     * PUT /api/laboratorios/{id}
     * Actualiza un laboratorio existente validando los datos.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> actualizar(@PathVariable Long id, @Valid @RequestBody Laboratorio laboratorio) {
        log.info("✏️ [PUT] Actualizando laboratorio con ID: {}", id);
        Laboratorio actualizado = service.update(id, laboratorio);
        return ResponseEntity.ok(actualizado);
    }

    // ============================================================
    // 🔸 Semana 2 → Eliminar laboratorio con control de errores
    // ============================================================
    /**
     * DELETE /api/laboratorios/{id}
     * Elimina un laboratorio existente por su ID.
     * Si no existe, se lanza excepción ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("🗑️ [DELETE] Eliminando laboratorio con ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // 🔸 Semana 2 → Nuevo endpoint: buscar por tipo (findByTipo)
    // ============================================================
    /**
     * GET /api/laboratorios/tipo/{tipo}
     * Retorna todos los laboratorios de un tipo específico.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Laboratorio>> buscarPorTipo(@PathVariable String tipo) {
        log.info("📖 [GET] Buscando laboratorios del tipo: {}", tipo);
        List<Laboratorio> laboratorios = service.findByTipo(tipo);
        if (laboratorios.isEmpty()) {
            log.warn("⚠️ No se encontraron laboratorios del tipo: {}", tipo);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(laboratorios);
    }
}
