package com.ciberrecursos.api.services;

import com.ciberrecursos.api.entities.Equipo;
import com.ciberrecursos.api.entities.Reservacion;
import com.ciberrecursos.api.repositories.EquipoRepository;
import com.ciberrecursos.api.repositories.ReservacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservacionService {
    private final ReservacionRepository reservacionRepository;
    private final EquipoRepository equipoRepository;

    public ReservacionService(ReservacionRepository reservacionRepository, EquipoRepository equipoRepository) {
        this.reservacionRepository = reservacionRepository;
        this.equipoRepository = equipoRepository;
    }

    public List<Reservacion> listar() { return reservacionRepository.findAll(); }

    public Optional<Reservacion> obtener(Long id) { return reservacionRepository.findById(id); }

    @Transactional
    public Reservacion agregar(Reservacion r) {
        if (!r.getHoraFin().isAfter(r.getHoraInicio())) {
            throw new IllegalArgumentException("hora_fin debe ser mayor que hora_inicio");
        }
        var overlaps = reservacionRepository.findOverlaps(
                r.getEquipo().getCodEquipo(), r.getFecha(), r.getHoraInicio(), r.getHoraFin()
        );
        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("Conflicto: existe una reserva solapada para ese equipo y horario");
        }
        return reservacionRepository.save(r);
    }

    public void eliminar(Long id) { reservacionRepository.deleteById(id); }

    public List<Reservacion> filtrar(String usuario, Long codEquipo) {
        if (usuario != null && !usuario.isBlank()) return reservacionRepository.findByUsuarioIgnoreCase(usuario);
        if (codEquipo != null) {
            var e = equipoRepository.findById(codEquipo).orElse(null);
            if (e == null) return List.of();
            return reservacionRepository.findByEquipo(e);
        }
        return reservacionRepository.findAll();
    }

    public Map<String, Object> reporte(String usuario, Long codEquipo) {
        var base = filtrar(usuario, codEquipo);

        var detalles = base.stream().map(r -> Map.of(
                "numReservacion", r.getNumReservacion(),
                "fecha", r.getFecha().toString(),
                "horaInicio", r.getHoraInicio().toString(),
                "horaFin", r.getHoraFin().toString(),
                "usuario", r.getUsuario(),
                "codEquipo", r.getEquipo().getCodEquipo(),
                "nomEquipo", r.getEquipo().getNomEquipo(),
                "tipoEquipo", r.getEquipo().getTipoEquipo()
        )).toList();

        Map<Long, Double> horasPorEquipo = new HashMap<>();
        for (var r : base) {
            double horas = Duration.between(r.getHoraInicio(), r.getHoraFin()).toMinutes() / 60.0;
            horasPorEquipo.merge(r.getEquipo().getCodEquipo(), horas, Double::sum);
        }

        var resumen = base.stream()
                .collect(Collectors.groupingBy(r -> r.getEquipo().getCodEquipo()))
                .entrySet().stream().map(e -> {
                    Long codE = e.getKey();
                    String nom = e.getValue().get(0).getEquipo().getNomEquipo();
                    double horas = Math.round(horasPorEquipo.getOrDefault(codE, 0.0) * 100.0)/100.0;
                    return Map.of("codEquipo", codE, "nomEquipo", nom, "horasTotales", horas);
                }).toList();

        return Map.of("reservas", detalles, "resumenHorasPorEquipo", resumen);
    }
}
