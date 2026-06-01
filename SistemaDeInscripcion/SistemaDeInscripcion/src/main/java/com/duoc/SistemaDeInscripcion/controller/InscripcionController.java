package com.duoc.sistemadeinscripcion.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemadeinscripcion.dto.InscripcionResponseDTO;
import com.duoc.sistemadeinscripcion.service.InscripcionService;

@RestController
@RequestMapping("/api")
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    @PostMapping("/inscripciones")
    public ResponseEntity<InscripcionResponseDTO> crearInscripcion(
            @RequestBody Map<String, Object> request) {

        Long estudianteId = Long.valueOf(request.get("estudianteId").toString());
        List<Long> cursoIds = ((List<?>) request.get("cursoIds"))
            .stream()
            .map(id -> Long.valueOf(id.toString()))
            .toList();

        InscripcionResponseDTO boleta = service.createInscripcion(estudianteId, cursoIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(boleta);
    }

    @GetMapping("/inscripciones/estudiante/{estudianteId}")
    public ResponseEntity<?> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(service.getInscripcionesByEstudiante(estudianteId));
    }

    @DeleteMapping("/inscripciones/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        return service.deleteInscripcion(id)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}