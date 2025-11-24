package com.fullstack.clinica.laboratory.service;

import com.fullstack.clinica.exception.ResourceNotFoundException; // Semana 2 → la crearemos en paso 4
import com.fullstack.clinica.laboratory.model.Laboratory;
import com.fullstack.clinica.laboratory.repository.LaboratoryRepository;

import lombok.extern.slf4j.Slf4j; // Lombok → para logs sin crear Logger manual
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ===============================================================
 * 📘 Clase: LaboratorioService
 * ---------------------------------------------------------------
 * Capa intermedia entre el controlador (API REST) y el repositorio
 * (Base de Datos).
 *
 * 🔹 Semana 1:
 * - CRUD básico sin reglas de negocio.
 * 🔹 Semana 2:
 * - Se agregan logs con @Slf4j para trazabilidad profesional.
 * - Se incorporan validaciones de negocio antes de guardar/eliminar.
 * - Se integrará manejo global de excepciones (GlobalExceptionHandler).
 * ===============================================================
 */
@Slf4j
@Service // Marca esta clase como "servicio" dentro del contexto de Spring
public class LaboratoryService {

    // Inyección del repositorio para interactuar con la base de datos
    private final LaboratoryRepository repository;

    /**
     * Constructor principal (inyección de dependencias por constructor).
     * Spring se encarga de pasar automáticamente una instancia de LaboratorioRepository.
     */
    public LaboratoryService(LaboratoryRepository repository) {
        this.repository = repository;
    }

    // ============================================================
    // 🔸 Semana 1 → Métodos CRUD básicos
    // ============================================================

    /**
     * Obtiene todos los laboratorios desde la base de datos.
     */
    public List<Laboratory> findAll() {
        log.info("📚 Consultando todos los laboratorios en la base de datos");
        return repository.findAll();
    }

    /**
     * Busca un laboratorio por su ID.
     * Si no existe, lanza una excepción personalizada.
     */
    public Laboratory findById(Long id) {
        log.info("🔍 Buscando laboratorio con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el laboratorio con ID: " + id));
    }

    // ============================================================
    // 🔸 Semana 2 → Nuevas validaciones y reglas de negocio
    // ============================================================

    /**
     * Guarda un nuevo laboratorio o actualiza uno existente.
     * 
     * Reglas de negocio agregadas (Semana 2):
     * 1️⃣ No permitir guardar un laboratorio con el mismo título y autor ya existentes.
     * 2️⃣ Registrar logs de INFO y WARN según la operación.
     */
    public Laboratory save(Laboratory laboratorio) {
        log.info("💾 Guardando laboratorio: {} - {}", laboratorio.getName());

        // Validación de duplicado: mismo nombre
        boolean existe = repository.findAll().stream()
                .anyMatch(l -> l.getName().equalsIgnoreCase(laboratorio.getName()));

        if (existe) {
            log.warn("⚠️ Intento de guardar un laboratorio duplicado: {}", laboratorio.getName());
            throw new IllegalArgumentException("Ya existe un laboratorio con el mismo título y autor.");
        }

        Laboratory guardado = repository.save(laboratorio);
        log.info("✅ laboratorio guardado correctamente con ID: {}", guardado.getId());
        return guardado;
    }

    /**
     * Actualiza un laboratorio existente.
     * Si no existe, lanza excepción de recurso no encontrado.
     */
    public Laboratory update(Long id, Laboratory datosActualizados) {
        log.info("✏️ Actualizando laboratorio con ID: {}", id);

        Laboratory laboratorioExistente = findById(id); // lanza excepción si no existe

        laboratorioExistente.setName(datosActualizados.getName());
        laboratorioExistente.setDescription(datosActualizados.getDescription());
        laboratorioExistente.setState(datosActualizados.getState());


        Laboratory actualizado = repository.save(laboratorioExistente);
        log.info("✅ laboratorio actualizado correctamente: {}", actualizado.getName());
        return actualizado;
    }

    /**
     * Elimina un laboratorio por su ID.
     * Si el ID no existe, lanza excepción ResourceNotFoundException.
     */
    public void delete(Long id) {
        log.info("🗑️ Eliminando laboratorio con ID: {}", id);

        if (!repository.existsById(id)) {
            log.error("❌ No se puede eliminar. El laboratorio con ID {} no existe.", id);
            throw new ResourceNotFoundException("No existe el laboratorio con ID: " + id);
        }

        repository.deleteById(id);
        log.info("✅ laboratorio eliminado correctamente.");
    }

    // ============================================================
    // Consultas personalizadas
    // ============================================================
    /**
     * Devuelve una lista de laboratorios filtrados por estado.
     */
    public List<Laboratory> findByState(String state) {
        log.info("📖 Buscando laboratorios con el estado: {}", state);
        return repository.findByState(state);
    }
}
