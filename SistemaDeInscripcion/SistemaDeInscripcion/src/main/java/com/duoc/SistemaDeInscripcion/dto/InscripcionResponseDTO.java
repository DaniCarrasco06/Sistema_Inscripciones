package com.duoc.sistemadeinscripcion.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponseDTO {

    private Long inscripcionId;
    private String nombreEstudiante;
    private String correoEstudiante;
    private List<CursoDTO> cursosInscritos;
    private int totalPagar;
    private LocalDate fechaInscripcion;
    private String mensaje;
}