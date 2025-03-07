package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
public class TemaController {

    @Autowired
    private TemaRepository temaRepository;

    @GetMapping
    public List<Tema> getAll() {
        return temaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable Long id) {
        Optional<Tema> tema = temaRepository.findById(id);
        if (tema.isPresent()) {
            return ResponseEntity.ok(tema.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/descricao")
    public List<Tema> getByDescricao(@RequestParam String descricao) {
        return temaRepository.findAllByDescricaoContainingIgnoreCase(descricao);
    }

    @PostMapping
    public ResponseEntity<Tema> post(@RequestBody Tema tema) {
        return ResponseEntity.ok(temaRepository.save(tema));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tema> put(@PathVariable Long id, @RequestBody Tema temaAtualizado) {
        Optional<Tema> temaExiste = temaRepository.findById(id);
        if (temaExiste.isPresent()) {
            Tema tema = temaExiste.get();
            tema.setDescricao(temaAtualizado.getDescricao());
            return ResponseEntity.ok(temaRepository.save(tema));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Tema> tema = temaRepository.findById(id);
        if (tema.isPresent()) {
            temaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
