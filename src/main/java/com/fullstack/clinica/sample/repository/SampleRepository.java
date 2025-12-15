package com.fullstack.clinica.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.clinica.sample.model.Sample;

import java.util.List;

/**
 * ===============================================================
 * 📘 Interfaz: SampleRepository
 * ---------------------------------------------------------------
 * Capa de acceso a datos (DAO) para la entidad Muestra.
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
public interface SampleRepository extends JpaRepository<Sample, Long> {

    // ============================================================
    // Consulta personalizada (criterio de complejidad)
    // ============================================================
    /**
     * Busca todos los laboratorios según su estado.
     * 
     * @param laboratory (ACTIVA, INACTIVA, PROCESADA).
     * @return Lista de laboratorios que pertenecen al estado indicado.
     */
    List<Sample> findByLaboratory(String laboratory);
}
