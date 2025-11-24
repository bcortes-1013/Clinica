package com.fullstack.clinica.laboratory.controller;

import jakarta.validation.Valid; // Semana 2 → para activar Bean Validation
import lombok.extern.slf4j.Slf4j; // Semana 2 → logging profesional
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.clinica.laboratory.model.Laboratory;
import com.fullstack.clinica.laboratory.service.LaboratoryService;

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
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    private final LaboratoryService service;

    public LaboratoryController(LaboratoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Laboratory>> listar() {
        log.info("📚 [GET] Solicitando listado completo de laboratorios");
        List<Laboratory> laboratorios = service.findAll();
        return ResponseEntity.ok(laboratorios);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Laboratory> obtenerPorId(@PathVariable Long id) {
        log.info("🔍 [GET] Buscando laboratorio con ID: {}", id);
        Laboratory laboratorio = service.findById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(laboratorio);
    }

    @PostMapping
    public ResponseEntity<Laboratory> crear(@Valid @RequestBody Laboratory laboratorio) {
        log.info("📝 [POST] Creando laboratorio: {} - {}", laboratorio.getName());
        Laboratory nuevo = service.save(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Laboratory> actualizar(@PathVariable Long id, @Valid @RequestBody Laboratory laboratorio) {
        log.info("✏️ [PUT] Actualizando laboratorio con ID: {}", id);
        Laboratory actualizado = service.update(id, laboratorio);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("🗑️ [DELETE] Eliminando laboratorio con ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Laboratory>> buscarPorTipo(@PathVariable String state) {
        log.info("📖 [GET] Buscando laboratorios con el estado: {}", state);
        List<Laboratory> laboratorios = service.findByState(state);
        if (laboratorios.isEmpty()) {
            log.warn("⚠️ No se encontraron laboratorios con el estado: {}", state);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(laboratorios);
    }
}
