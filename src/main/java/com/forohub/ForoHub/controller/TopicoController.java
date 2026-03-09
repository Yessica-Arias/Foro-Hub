package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.model.Topico;
import com.forohub.ForoHub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public Page<Topico> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        return topicoRepository.findAll(paginacion);
    }

    @GetMapping("/primeros")
    public List<Topico> listarPrimerosTopicos() {
        return topicoRepository.findTop10ByOrderByFechaCreacionAsc();
    }

    @GetMapping("/curso")
    public List<Topico> buscarPorCurso(@RequestParam String curso) {
        return topicoRepository.findByCurso(curso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detallarTopico(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@RequestBody @Valid Topico topico) {
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.ok(nuevoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id,
                                                   @RequestBody @Valid Topico datosActualizados) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topico.setTitulo(datosActualizados.getTitulo());
                    topico.setMensaje(datosActualizados.getMensaje());
                    topico.setAutor(datosActualizados.getAutor());
                    topico.setCurso(datosActualizados.getCurso());
                    topico.setEstatus(datosActualizados.getEstatus());
                    return ResponseEntity.ok(topicoRepository.save(topico));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topicoRepository.delete(topico);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
