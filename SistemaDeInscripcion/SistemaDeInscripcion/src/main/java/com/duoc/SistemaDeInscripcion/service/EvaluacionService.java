package com.duoc.SistemaDeInscripcion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.SistemaDeInscripcion.exception.ResourceNotFoundException;
import com.duoc.SistemaDeInscripcion.model.Evaluacion;
import com.duoc.SistemaDeInscripcion.repository.EvaluacionRepository;
import com.duoc.SistemaDeInscripcion.repository.InscripcionRepository;


@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository repo;

    @Autowired
    private InscripcionRepository inscripcionRepo;

    public List<Evaluacion> getEvaluacionesByInscripcion(Long inscripcionId) {
        return repo.findByInscripcionId(inscripcionId);
    }

    public Evaluacion createEvaluacion(Evaluacion evaluacion) {
        Long inscripcionId = evaluacion.getInscripcion().getId();
        if (!inscripcionRepo.existsById(inscripcionId)) {
            throw new ResourceNotFoundException(
                "Inscripción no encontrada con ID: " + inscripcionId);
        }
        return repo.save(evaluacion);
    }

    public boolean deleteEvaluacion(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}

