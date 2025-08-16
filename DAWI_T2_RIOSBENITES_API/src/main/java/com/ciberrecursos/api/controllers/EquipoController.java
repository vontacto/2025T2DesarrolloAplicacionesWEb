package com.ciberrecursos.api.controllers;

import com.ciberrecursos.api.entities.Equipo;
import com.ciberrecursos.api.repositories.EquipoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoRepository repo;

    public EquipoController(EquipoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Equipo> listar() { return repo.findAll(); }

    @PostMapping
    public Equipo crear(@RequestBody Equipo e) { return repo.save(e); }
}
