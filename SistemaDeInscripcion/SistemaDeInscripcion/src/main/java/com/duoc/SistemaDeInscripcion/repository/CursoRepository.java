package com.duoc.sistemadeinscripcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.sistemadeinscripcion.model.Curso;

import java.util.List;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    // Buscar cursos por nombre del profesor
    List<Curso> findByProfesorId(Long profesorId);
    // Verificar si ya existe un curso con ese nombre
    boolean existsByNombreIgnoreCase(String nombre);
}

