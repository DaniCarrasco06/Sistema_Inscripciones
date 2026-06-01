package com.duoc.sistemadeinscripcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.sistemadeinscripcion.model.Evaluacion;

import java.util.List;


@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByInscripcionId(Long inscripcionId);
}
