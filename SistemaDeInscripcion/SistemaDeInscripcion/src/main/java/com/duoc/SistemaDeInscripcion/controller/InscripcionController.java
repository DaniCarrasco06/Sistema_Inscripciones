package com.duoc.sistemadeinscripcion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemadeinscripcion.dto.InscripcionResponseDTO;
import com.duoc.sistemadeinscripcion.model.Inscripcion;
import com.duoc.sistemadeinscripcion.service.InscripcionService;


@RestController
@RequestMapping("/api")
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    // GET inscripciones por ID del curso
    @GetMapping("/inscripciones/curso/{cursoId}")
    public ResponseEntity<List<Inscripcion>> obtenerPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(service.getInscripcionesByCurso(cursoId));
    }

    // GET inscripciones por ID del estudiante
    @GetMapping("/inscripciones/estudiante/{estudianteId}")
    public ResponseEntity<List<Inscripcion>> obtenerPorEstudiante(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(service.getInscripcionesByEstudiante(estudianteId));
    }

    // POST inscribir estudiante 
    @PostMapping("/inscripciones")
    public ResponseEntity<InscripcionResponseDTO> crearInscripcion(
            @RequestBody Inscripcion inscripcion) {
        InscripcionResponseDTO boleta = service.createInscripcion(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(boleta);
    }

    // DELETE eliminar inscripción por ID
    @DeleteMapping("/inscripciones/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        boolean eliminada = service.deleteInscripcion(id);
        return eliminada
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}

