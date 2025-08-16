package com.ciberrecursos.api.repositories;

import com.ciberrecursos.api.entities.Reservacion;
import com.ciberrecursos.api.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

    @Query("""
       SELECT r FROM Reservacion r
       WHERE r.equipo.codEquipo = :codEquipo
         AND r.fecha = :fecha
         AND (:inicio < r.horaFin AND :fin > r.horaInicio)
    """)
    List<Reservacion> findOverlaps(@Param("codEquipo") Long codEquipo,
                                   @Param("fecha") LocalDate fecha,
                                   @Param("inicio") LocalTime inicio,
                                   @Param("fin") LocalTime fin);

    List<Reservacion> findByUsuarioIgnoreCase(String usuario);
    List<Reservacion> findByEquipo(Equipo equipo);
}
