package com.duoc.SistemaDeInscripcion.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INSCRIPCION")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", referencedColumnName = "id")
    private Usuario estudiante;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    @PrePersist
    public void asignarFecha() {
        if (this.fechaInscripcion == null) {
            this.fechaInscripcion = LocalDate.now();
        }
    }
}

