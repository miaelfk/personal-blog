package com.generation.blogpessoal.controller;  

import java.util.List;  
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.generation.blogpessoal.repository.TemaRepository;

@RestController  
@RequestMapping("/postagens")  
public class PostagemController {  

    @Autowired  
    private PostagemRepository postagemRepository;
    
    @Autowired
    private TemaRepository temaRepository;

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
    public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
        if (temaRepository.existsById(postagem.getTema().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postagemRepository.save(postagem));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    @PutMapping
    public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
        if (postagemRepository.existsById(postagem.getId())) {
            
            if (temaRepository.existsById(postagem.getTema().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(postagemRepository.save(postagem));
                
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
