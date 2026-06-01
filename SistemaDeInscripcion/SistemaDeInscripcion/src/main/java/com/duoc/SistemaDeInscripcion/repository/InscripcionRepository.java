package com.duoc.sistemadeinscripcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.sistemadeinscripcion.model.Inscripcion;

import java.util.List;


@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    // Buscar inscripciones por ID del curso
    List<Inscripcion> findByCursoId(Long cursoId);
    // Buscar inscripciones por ID del estudiante
    List<Inscripcion> findByEstudianteId(Long estudianteId);
    // Verificar si el estudiante ya está inscrito en ese curso
    boolean existsByCursoIdAndEstudianteId(Long cursoId, Long estudianteId);
}

