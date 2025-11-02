package com.fullstack.clinica.laboratorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.clinica.laboratorio.model.Laboratorio;

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
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    // ============================================================
    // 🔸 Semana 2 → Consulta personalizada (criterio de complejidad)
    // ============================================================
    /**
     * Busca todos los laboratorios de un tipo específico.
     * 
     * Spring Data JPA interpreta automáticamente el nombre del método y
     * genera la consulta equivalente:
     * 
     * SELECT * FROM LABORATORIO WHERE TIPO = :tipo
     * 
     * @param tipo Nombre del tipo (coincidencia exacta o parcial).
     * @return Lista de laboratorios que pertenecen al tipo indicado.
     */
    List<Laboratorio> findByTipo(String tipo);
}
