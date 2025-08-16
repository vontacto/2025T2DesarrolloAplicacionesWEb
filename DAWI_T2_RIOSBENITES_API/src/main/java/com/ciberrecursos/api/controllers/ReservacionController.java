package com.ciberrecursos.api.controllers;

import com.ciberrecursos.api.entities.Reservacion;
import com.ciberrecursos.api.services.ReservacionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservacionController {
    private final ReservacionService service;

    public ReservacionController(ReservacionService service) { this.service = service; }

    @GetMapping
    public List<Reservacion> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Reservacion> obtener(@PathVariable Long id) {
        return service.obtener(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> agregar(@RequestBody Reservacion r) {
        try {
            var creado = service.agregar(r);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte")
    public Map<String, Object> reporte(@RequestParam(required = false) String usuario,
                                       @RequestParam(required = false) Long codEquipo) {
        return service.reporte(usuario, codEquipo);
    }
}
