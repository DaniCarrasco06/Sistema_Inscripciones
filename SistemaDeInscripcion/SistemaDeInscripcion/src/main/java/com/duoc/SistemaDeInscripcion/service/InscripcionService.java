package com.duoc.sistemadeinscripcion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.sistemadeinscripcion.dto.CursoDTO;
import com.duoc.sistemadeinscripcion.dto.InscripcionResponseDTO;
import com.duoc.sistemadeinscripcion.exception.ResourceNotFoundException;
import com.duoc.sistemadeinscripcion.model.Curso;
import com.duoc.sistemadeinscripcion.model.Inscripcion;
import com.duoc.sistemadeinscripcion.model.Usuario;
import com.duoc.sistemadeinscripcion.repository.CursoRepository;
import com.duoc.sistemadeinscripcion.repository.InscripcionRepository;
import com.duoc.sistemadeinscripcion.repository.UsuarioRepository;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository repo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Transactional
    public InscripcionResponseDTO createInscripcion(Long estudianteId, List<Long> cursoIds) {

        Usuario estudiante = usuarioRepo.findById(estudianteId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Estudiante no encontrado con ID: " + estudianteId));

        List<Curso> cursos = cursoRepo.findAllById(cursoIds);
        if (cursos.isEmpty()) {
            throw new ResourceNotFoundException("Ningún curso encontrado");
        }

        int total = cursos.stream()
            .mapToInt(Curso::getCosto)
            .sum();

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCursos(cursos);
        inscripcion.setTotalPagar(total);
        Inscripcion guardada = repo.save(inscripcion);

        List<CursoDTO> cursosDTO = cursos.stream()
            .map(c -> CursoDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .nombreProfesor(c.getProfesor() != null
                    ? c.getProfesor().getNombre() : "Sin asignar")
                .duracionHoras(c.getDuracionHoras())
                .costo(c.getCosto())
                .build())
            .toList();

        return InscripcionResponseDTO.builder()
            .inscripcionId(guardada.getId())
            .nombreEstudiante(estudiante.getNombre())
            .correoEstudiante(estudiante.getCorreo())
            .cursosInscritos(cursosDTO)
            .totalPagar(total)
            .fechaInscripcion(guardada.getFechaInscripcion())
            .mensaje("Inscripción exitosa. ¡Bienvenido a los cursos!")
            .build();
    }

    public List<Inscripcion> getInscripcionesByEstudiante(Long estudianteId) {
        return repo.findByEstudianteId(estudianteId);
    }

    public boolean deleteInscripcion(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}