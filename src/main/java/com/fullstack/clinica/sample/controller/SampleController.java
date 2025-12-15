package com.fullstack.clinica.sample.controller;

import jakarta.validation.Valid; // Semana 2 → para activar Bean Validation
import lombok.extern.slf4j.Slf4j; // Semana 2 → logging profesional
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.clinica.sample.model.Sample;
import com.fullstack.clinica.sample.service.SampleService;

import java.util.List;

/**
 * ===============================================================
 * 📘 Clase: SampleController
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
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService service;

    public SampleController(SampleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Sample>> listar() {
        log.info("📚 [GET] Solicitando listado completo de laboratorios");
        List<Sample> laboratorios = service.findAll();
        return ResponseEntity.ok(laboratorios);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Sample> obtenerPorId(@PathVariable Long id) {
        log.info("🔍 [GET] Buscando laboratorio con ID: {}", id);
        Sample laboratorio = service.findById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(laboratorio);
    }

    @PostMapping
    public ResponseEntity<Sample> crear(@Valid @RequestBody Sample laboratorio) {
        log.info("📝 [POST] Creando laboratorio: {} - {}", laboratorio.getCode());
        Sample nuevo = service.save(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Sample> actualizar(@PathVariable Long id, @Valid @RequestBody Sample laboratorio) {
        log.info("✏️ [PUT] Actualizando laboratorio con ID: {}", id);
        Sample actualizado = service.update(id, laboratorio);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("🗑️ [DELETE] Eliminando laboratorio con ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/laboratory/{laboratory}")
    public ResponseEntity<List<Sample>> buscarPorTipo(@PathVariable String laboratory) {
        log.info("📖 [GET] Buscando muestras del laboratorio: {}", laboratory);
        List<Sample> laboratorios = service.findByLaboratory(laboratory);
        if (laboratorios.isEmpty()) {
            log.warn("⚠️ No se encontraron muestras con el laboratorio: {}", laboratory);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(laboratorios);
    }
}
