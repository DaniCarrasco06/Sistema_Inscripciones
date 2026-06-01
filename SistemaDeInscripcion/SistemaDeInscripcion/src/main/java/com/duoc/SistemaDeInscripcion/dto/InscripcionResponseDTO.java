package com.duoc.sistemadeinscripcion.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO que representa la boleta/resumen de una inscripción.
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponseDTO {

    private Long inscripcionId;

    // Datos del estudiante
    private String nombreEstudiante;
    private String correoEstudiante;

    // Datos del curso
    private Long cursoId;
    private String nombreCurso;
    private String descripcionCurso;
    private String nombreInstructor;
    private int duracionHoras;

    // Costo y fecha
    private int costoCurso;
    private LocalDate fechaInscripcion;

    // Mensaje de confirmación
    private String mensaje;
}



