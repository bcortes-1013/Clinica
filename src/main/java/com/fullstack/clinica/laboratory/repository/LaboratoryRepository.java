package com.fullstack.clinica.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.clinica.laboratory.model.Laboratory;

import java.util.List;

/**
 * ===============================================================
 * 📘 Interfaz: LaboratorioRepository
 * ---------------------------------------------------------------
 * Capa de acceso a datos (DAO) para la entidad Laboratorio.
 *
 * 🔹 Semana 1:
 * - Extiende JpaRepository y hereda los métodos CRUD básicos:
 * findAll(), findById(), save(), deleteById().
 *
 * 🔹 Semana 2:
 * - Se agregan consultas personalizadas utilizando la convención
 * de nombres de Spring Data JPA (métodos derivados).
 * - Se documentan los propósitos de cada consulta para aprendizaje.
 * ===============================================================
 */
@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {

    // ============================================================
    // Consulta personalizada (criterio de complejidad)
    // ============================================================
    /**
     * Busca todos los laboratorios según su estado.
     * 
     * @param state (ACTIVO o INACTIVO).
     * @return Lista de laboratorios que pertenecen al estado indicado.
     */
    List<Laboratory> findByState(String state);
}
