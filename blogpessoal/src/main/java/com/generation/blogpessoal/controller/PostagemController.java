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
import com.generation.blogpessoal.model.Postagem;  
import com.generation.blogpessoal.repository.PostagemRepository;  

@RestController  
@RequestMapping("/postagens")  
public class PostagemController {  

    @Autowired  
    private PostagemRepository postagemRepository;  

    @GetMapping  
    public List<Postagem> getAll() {  
        return postagemRepository.findAll();  
    }  
    
    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable Long id) {
        Optional<Postagem> postagem = postagemRepository.findById(id);
        if (postagem.isPresent()) {
            return ResponseEntity.ok(postagem.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/titulo")
    public List<Postagem> getByTitulo(@RequestParam("titulo") String titulo) {
        return postagemRepository.findAllByTituloContainingIgnoreCase(titulo);
    }
    @PostMapping
    public Postagem post(@RequestBody Postagem novaPostagem) {
        return postagemRepository.save(novaPostagem);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Postagem> put(@PathVariable Long id, @RequestBody Postagem postagemAtualizada) {
        Optional<Postagem> postagemExiste = postagemRepository.findById(id);
        if (postagemExiste.isPresent()) {
            Postagem postagem = postagemExiste.get();
            postagem.setTitulo(postagemAtualizada.getTitulo());
            postagem.setTexto(postagemAtualizada.getTexto());
            return ResponseEntity.ok(postagemRepository.save(postagem));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Postagem> postagem = postagemRepository.findById(id);
        if (postagem.isPresent()) {
            postagemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

