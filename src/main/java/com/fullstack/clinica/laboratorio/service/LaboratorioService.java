package com.fullstack.clinica.laboratorio.service;

import com.fullstack.clinica.exception.ResourceNotFoundException; // Semana 2 → la crearemos en paso 4
import com.fullstack.clinica.laboratorio.model.Laboratorio;
import com.fullstack.clinica.laboratorio.repository.LaboratorioRepository;

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
public class LaboratorioService {

    // Inyección del repositorio para interactuar con la base de datos
    private final LaboratorioRepository repository;

    /**
     * Constructor principal (inyección de dependencias por constructor).
     * Spring se encarga de pasar automáticamente una instancia de LaboratorioRepository.
     */
    public LaboratorioService(LaboratorioRepository repository) {
        this.repository = repository;
    }

    // ============================================================
    // 🔸 Semana 1 → Métodos CRUD básicos
    // ============================================================

    /**
     * Obtiene todos los laboratorios desde la base de datos.
     */
    public List<Laboratorio> findAll() {
        log.info("📚 Consultando todos los laboratorios en la base de datos");
        return repository.findAll();
    }

    /**
     * Busca un laboratorio por su ID.
     * Si no existe, lanza una excepción personalizada.
     */
    public Laboratorio findById(Long id) {
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
    public Laboratorio save(Laboratorio laboratorio) {
        log.info("💾 Guardando laboratorio: {} - {}", laboratorio.getNombre(), laboratorio.getTipo());

        // Validación de duplicado: mismo nombre
        boolean existe = repository.findAll().stream()
                .anyMatch(l -> l.getNombre().equalsIgnoreCase(laboratorio.getNombre()));

        if (existe) {
            log.warn("⚠️ Intento de guardar un laboratorio duplicado: {}", laboratorio.getNombre());
            throw new IllegalArgumentException("Ya existe un laboratorio con el mismo título y autor.");
        }

        Laboratorio guardado = repository.save(laboratorio);
        log.info("✅ laboratorio guardado correctamente con ID: {}", guardado.getId());
        return guardado;
    }

    /**
     * Actualiza un laboratorio existente.
     * Si no existe, lanza excepción de recurso no encontrado.
     */
    public Laboratorio update(Long id, Laboratorio datosActualizados) {
        log.info("✏️ Actualizando laboratorio con ID: {}", id);

        Laboratorio laboratorioExistente = findById(id); // lanza excepción si no existe

        laboratorioExistente.setNombre(datosActualizados.getNombre());
        laboratorioExistente.setDescripcion(datosActualizados.getDescripcion());
        laboratorioExistente.setTipo(datosActualizados.getTipo());
        laboratorioExistente.setCapacidad(datosActualizados.getCapacidad());
        laboratorioExistente.setEstado(datosActualizados.getEstado());
        laboratorioExistente.setTipoAnalisis(datosActualizados.getTipoAnalisis());


        Laboratorio actualizado = repository.save(laboratorioExistente);
        log.info("✅ laboratorio actualizado correctamente: {}", actualizado.getNombre());
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
    // 🔸 Semana 2 → Posibles extensiones (consultas personalizadas)
    // ============================================================
    /**
     * Devuelve una lista de laboratorios filtrados por tipo.
     * Se agregará el método findByTipo en el repositorio (Paso 5).
     */
    public List<Laboratorio> findByTipo(String tipo) {
        log.info("📖 Buscando laboratorios del tipo: {}", tipo);
        return repository.findByTipo(tipo);
    }
}
