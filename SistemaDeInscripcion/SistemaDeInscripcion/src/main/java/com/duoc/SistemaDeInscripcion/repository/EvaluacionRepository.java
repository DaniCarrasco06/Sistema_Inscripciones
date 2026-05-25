package com.duoc.SistemaDeInscripcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.SistemaDeInscripcion.model.Evaluacion;

import java.util.List;


@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByInscripcionId(Long inscripcionId);
}
