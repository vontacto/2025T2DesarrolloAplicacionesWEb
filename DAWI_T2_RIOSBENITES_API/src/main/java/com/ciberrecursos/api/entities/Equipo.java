package com.ciberrecursos.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "equipo")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Equipo {
    @Id
    @Column(name = "cod_equipo")
    private Long codEquipo;

    @NotBlank
    @Column(name = "nom_equipo")
    private String nomEquipo;

    @NotBlank
    @Column(name = "tipo_equipo")
    private String tipoEquipo;

    @NotBlank
    @Column(name = "estado")
    private String estado;
}
