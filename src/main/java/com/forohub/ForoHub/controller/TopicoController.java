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

    @PostMapping
    public ResponseEntity<?> crearTopico(@RequestBody @Valid Topico topico) {
        if (topicoRepository.existsByTituloAndMensaje(topico.getTitulo(), topico.getMensaje())) {
            return ResponseEntity.badRequest().body("Error: Ya existe un tópico con el mismo titulo y mensaje");
        }
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.ok(nuevoTopico);
    }

    @GetMapping("/primeros")
    public List<Topico> listarPimerosTopicos() {
        return topicoRepository.findTop10ByOrderByFechaCreacionAsc();
    }

    @GetMapping("/curso")
    public List<Topico> buscarPorCurso(@RequestParam String curso) {
        return topicoRepository.findByCurso(curso);
    }

    @GetMapping
    public Page<Topico> listar(@PageableDefault(size = 10, sort = "fechaCreacion")Pageable paginacion) {
        return topicoRepository.findAll(paginacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detallarTopico(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizaTopico(@PathVariable Long id, @RequestBody @Valid Topico datosActualizados) {

        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();

            topico.setTitulo(datosActualizados.getTitulo());
            topico.setMensaje(datosActualizados.getMensaje());
            topico.setAutor(datosActualizados.getAutor());
            topico.setCurso(datosActualizados.getCurso());
            topico.setEstatus(datosActualizados.getEstatus());

            Topico topicoActualizado = topicoRepository.save(topico);
            return ResponseEntity.ok(topicoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {

        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
