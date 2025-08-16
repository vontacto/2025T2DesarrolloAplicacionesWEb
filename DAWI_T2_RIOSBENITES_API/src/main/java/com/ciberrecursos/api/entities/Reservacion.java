package com.ciberrecursos.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "reservacion")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_reservacion")
    private Long numReservacion;

    @NotNull
    private LocalDate fecha;

    @NotNull
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @NotBlank
    private String usuario;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_equipo", nullable = false)
    private Equipo equipo;

}
