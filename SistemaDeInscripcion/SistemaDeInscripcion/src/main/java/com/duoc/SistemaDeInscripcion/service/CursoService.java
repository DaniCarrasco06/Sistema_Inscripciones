package com.duoc.sistemadeinscripcion.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.sistemadeinscripcion.dto.CursoDTO;
import com.duoc.sistemadeinscripcion.exception.ResourceNotFoundException;
import com.duoc.sistemadeinscripcion.model.Curso;
import com.duoc.sistemadeinscripcion.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repo;

    public List<CursoDTO> getAllCursos() {
        return repo.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public CursoDTO getCursoById(Long id) {
        return toDTO(repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Curso no encontrado con ID: " + id)));
    }

    public CursoDTO createCurso(Curso curso) {
        if (repo.existsByNombreIgnoreCase(curso.getNombre())) {
            throw new IllegalArgumentException(
                "Ya existe un curso con el nombre: " + curso.getNombre());
        }
        return toDTO(repo.save(curso));
    }

    public Optional<CursoDTO> updateCurso(Long id, Curso curso) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                "Curso no encontrado con ID: " + id);
        }
        curso.setId(id);
        return Optional.of(toDTO(repo.save(curso)));
    }

    public boolean deleteCurso(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private CursoDTO toDTO(Curso c) {
        return CursoDTO.builder()
            .id(c.getId())
            .nombre(c.getNombre())
            .descripcion(c.getDescripcion())
            .nombreProfesor(c.getProfesor() != null
                ? c.getProfesor().getNombre() : "Sin asignar")
            .duracionHoras(c.getDuracionHoras())
            .costo(c.getCosto())
            .build();
    }
}