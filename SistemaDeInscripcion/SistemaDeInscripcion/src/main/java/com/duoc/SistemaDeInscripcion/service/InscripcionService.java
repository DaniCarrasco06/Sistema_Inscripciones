package com.duoc.sistemadeinscripcion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    // GET: Inscripciones por ID del curso
    public List<Inscripcion> getInscripcionesByCurso(Long cursoId) {
        return repo.findByCursoId(cursoId);
    }

    // GET: Inscripciones por ID del estudiante
    public List<Inscripcion> getInscripcionesByEstudiante(Long estudianteId) {
        return repo.findByEstudianteId(estudianteId);
    }

    // POST: Crear inscripción y retornar boleta con resumen
    @Transactional
    public InscripcionResponseDTO createInscripcion(Inscripcion inscripcion) {


        // 1. Verificar que el curso existe
        Long cursoId = inscripcion.getCurso().getId();
        Curso curso = cursoRepo.findById(cursoId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Curso no encontrado con ID: " + cursoId));


        // 2. Verificar que el estudiante existe
        Long estudianteId = inscripcion.getEstudiante().getId();
        Usuario estudiante = usuarioRepo.findById(estudianteId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Estudiante no encontrado con ID: " + estudianteId));


        // 3. Verificar que el estudiante no esté ya inscrito
        if (repo.existsByCursoIdAndEstudianteId(cursoId, estudianteId)) {
            throw new IllegalArgumentException(
                "El estudiante ya está inscrito en este curso");
        }


        // 4. Asignar objetos completos y guardar
        inscripcion.setCurso(curso);
        inscripcion.setEstudiante(estudiante);
        Inscripcion guardada = repo.save(inscripcion);


        // 5. Construir y retornar la boleta/resumen
        return InscripcionResponseDTO.builder()
            .inscripcionId(guardada.getId())
            .nombreEstudiante(estudiante.getNombre())
            .correoEstudiante(estudiante.getCorreo())
            .cursoId(curso.getId())
            .nombreCurso(curso.getNombre())
            .descripcionCurso(curso.getDescripcion())
            .nombreInstructor(curso.getProfesor() != null
                ? curso.getProfesor().getNombre() : "Sin asignar")
            .duracionHoras(curso.getDuracionHoras())
            .costoCurso(curso.getCosto())
            .fechaInscripcion(guardada.getFechaInscripcion())
            .mensaje("Inscripción exitosa. ¡Bienvenido al curso!")
            .build();
    }

    // DELETE: Eliminar inscripción
    public boolean deleteInscripcion(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}

