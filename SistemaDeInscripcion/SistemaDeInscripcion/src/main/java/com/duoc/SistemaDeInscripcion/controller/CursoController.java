package com.duoc.sistemadeinscripcion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemadeinscripcion.dto.CursoDTO;
import com.duoc.sistemadeinscripcion.model.Curso;
import com.duoc.sistemadeinscripcion.service.CursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping("/cursos")
    public ResponseEntity<List<CursoDTO>> obtenerCursos() {
        return ResponseEntity.ok(service.getAllCursos());
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<CursoDTO> obtenerCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCursoById(id));
    }

    @PostMapping("/cursos")
    public ResponseEntity<CursoDTO> crearCurso(@Valid @RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCurso(curso));
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<CursoDTO> modificarCurso(
            @PathVariable Long id, @Valid @RequestBody Curso curso) {
        return service.updateCurso(id, curso)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        return service.deleteCurso(id)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}