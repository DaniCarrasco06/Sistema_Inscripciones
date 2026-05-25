package com.duoc.SistemaDeInscripcion.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.SistemaDeInscripcion.model.Evaluacion;
import com.duoc.SistemaDeInscripcion.service.EvaluacionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class EvaluacionController {

    @Autowired
    private EvaluacionService service;

    // GET evaluaciones por inscripción
    @GetMapping("/evaluaciones/inscripcion/{inscripcionId}")
    public ResponseEntity<List<Evaluacion>> obtenerPorInscripcion(
            @PathVariable Long inscripcionId) {
        return ResponseEntity.ok(service.getEvaluacionesByInscripcion(inscripcionId));
    }

    // POST crear evaluación
    @PostMapping("/evaluaciones")
    public ResponseEntity<Evaluacion> crearEvaluacion(
            @Valid @RequestBody Evaluacion evaluacion) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.createEvaluacion(evaluacion));
    }

    // DELETE eliminar evaluación
    @DeleteMapping("/evaluaciones/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
        boolean eliminada = service.deleteEvaluacion(id);
        return eliminada
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}


