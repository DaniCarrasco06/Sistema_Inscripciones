package com.duoc.sistemadeinscripcion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CURSO")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_profesor", referencedColumnName = "id")
    private Usuario profesor;

    @Column(name = "duracion_horas")
    @Positive(message = "La duración debe ser mayor a 0")
    private int duracionHoras;

    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private int costo;
}


