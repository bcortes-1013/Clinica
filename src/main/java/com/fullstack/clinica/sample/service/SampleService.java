package com.fullstack.clinica.sample.service;

import com.fullstack.clinica.exception.ResourceNotFoundException; // Semana 2 → la crearemos en paso 4
import com.fullstack.clinica.sample.model.Sample;
import com.fullstack.clinica.sample.repository.SampleRepository;

import lombok.extern.slf4j.Slf4j; // Lombok → para logs sin crear Logger manual
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ===============================================================
 * 📘 Clase: SampleService
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
public class SampleService {

    // Inyección del repositorio para interactuar con la base de datos
    private final SampleRepository repository;

    /**
     * Constructor principal (inyección de dependencias por constructor).
     * Spring se encarga de pasar automáticamente una instancia de LaboratorioRepository.
     */
    public SampleService(SampleRepository repository) {
        this.repository = repository;
    }

    // ============================================================
    // 🔸 Semana 1 → Métodos CRUD básicos
    // ============================================================

    /**
     * Obtiene todos los muestras desde la base de datos.
     */
    public List<Sample> findAll() {
        log.info("📚 Consultando todos las muestras en la base de datos");
        return repository.findAll();
    }

    /**
     * Busca una muestra por su ID.
     * Si no existe, lanza una excepción personalizada.
     */
    public Sample findById(Long id) {
        log.info("🔍 Buscando muestra con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la muestra con ID: " + id));
    }

    // ============================================================
    // 🔸 Semana 2 → Nuevas validaciones y reglas de negocio
    // ============================================================

    /**
     * Guarda una nueva muestra o actualiza una existente.
     * 
     * Reglas de negocio agregadas (Semana 2):
     * 1️⃣ No permitir guardar un muestra con el mismo código.
     * 2️⃣ Registrar logs de INFO y WARN según la operación.
     */
    public Sample save(Sample muestra) {
        log.info("💾 Guardando muestra: {} - {}", muestra.getCode());

        // Validación de duplicado: mismo nombre
        boolean existe = repository.findAll().stream()
                .anyMatch(s -> s.getCode().equalsIgnoreCase(muestra.getCode()));

        if (existe) {
            log.warn("⚠️ Intento de guardar una muestra duplicada: {}", muestra.getCode());
            throw new IllegalArgumentException("Ya existe una muestra con el mismo código.");
        }

        Sample guardado = repository.save(muestra);
        log.info("✅ Muestra guardada correctamente con ID: {}", guardado.getId());
        return guardado;
    }

    /**
     * Actualiza un muestra existente.
     * Si no existe, lanza excepción de recurso no encontrado.
     */
    public Sample update(Long id, Sample datosActualizados) {
        log.info("✏️ Actualizando muestra con ID: {}", id);

        Sample sampleExist = findById(id); // lanza excepción si no existe

        sampleExist.setCode(datosActualizados.getCode());
        sampleExist.setDescription(datosActualizados.getDescription());

        Sample actualizado = repository.save(sampleExist);
        log.info("✅ Muestra actualizada correctamente: {}", actualizado.getCode());
        return actualizado;
    }

    /**
     * Elimina un muestra por su ID.
     * Si el ID no existe, lanza excepción ResourceNotFoundException.
     */
    public void delete(Long id) {
        log.info("🗑️ Eliminando muestra con ID: {}", id);

        if (!repository.existsById(id)) {
            log.error("❌ No se puede eliminar. La muestra con ID {} no existe.", id);
            throw new ResourceNotFoundException("No existe la muestra con ID: " + id);
        }

        repository.deleteById(id);
        log.info("✅ muestra eliminado correctamente.");
    }

    // ============================================================
    // Consultas personalizadas
    // ============================================================
    /**
     * Devuelve una lista de muestras filtrados por estado.
     */
    public List<Sample> findByLaboratory(String laboratory) {
        log.info("📖 Buscando muestras del laboratorio: {}", laboratory);
        return repository.findByLaboratory(laboratory);
    }
}
