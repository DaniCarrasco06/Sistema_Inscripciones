package com.duoc.sistemadeinscripcion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String nombreProfesor;
    private int duracionHoras;
    private int costo;
}