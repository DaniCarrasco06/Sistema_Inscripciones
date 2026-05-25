package com.duoc.SistemaDeInscripcion.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.SistemaDeInscripcion.model.Curso;
import com.duoc.SistemaDeInscripcion.service.CursoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    private CursoService service;

    // GET todos los cursos 
    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> obtenerCursos() {
        return ResponseEntity.ok(service.getAllCursos());
    }

    // GET curso por ID
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCursoById(id));
    }

    // POST agregar curso
    @PostMapping("/cursos")
    public ResponseEntity<Curso> crearCurso(@Valid @RequestBody Curso curso) {
        Curso nuevo = service.createCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // PUT modificar curso
    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> modificarCurso(
            @PathVariable Long id, @Valid @RequestBody Curso curso) {
        Optional<Curso> actualizado = service.updateCurso(id, curso);
        return actualizado
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE eliminar curso
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        boolean eliminado = service.deleteCurso(id);
        return eliminado
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}

