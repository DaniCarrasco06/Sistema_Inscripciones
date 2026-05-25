package com.duoc.SistemaDeInscripcion.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.SistemaDeInscripcion.exception.ResourceNotFoundException;
import com.duoc.SistemaDeInscripcion.model.Curso;
import com.duoc.SistemaDeInscripcion.repository.CursoRepository;


@Service
public class CursoService {


    @Autowired
    private CursoRepository repo;

    // Consultar todos los cursos (GET)
    public List<Curso> getAllCursos() {
        return repo.findAll();
    }

    // Consultar un curso por ID (GET)
    public Curso getCursoById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Curso no encontrado con ID: " + id));
    }

    public Curso createCurso(Curso curso) {
        if (repo.existsByNombreIgnoreCase(curso.getNombre())) {
            throw new IllegalArgumentException(
                "Ya existe un curso con el nombre: " + curso.getNombre());
        }
        return repo.save(curso);
    }

    public Optional<Curso> updateCurso(Long id, Curso curso) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                "Curso no encontrado con ID: " + id);
        }
        curso.setId(id);
        return Optional.of(repo.save(curso));
    }

    public boolean deleteCurso(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}


