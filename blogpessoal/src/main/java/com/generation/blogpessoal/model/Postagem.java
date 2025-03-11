package com.generation.blogpessoal.model;  

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;  
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;  
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;  

@Entity  
@Table(name = "tb_postagens")  
public class Postagem {  

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  

    private String titulo;  
    private String texto;  

    // Get e Set
    public Long getId() { return id; }  
    public void setId(Long id) { this.id = id; }  

    public String getTitulo() { return titulo; }  
    public void setTitulo(String titulo) { this.titulo = titulo; }  

    public String getTexto() { return texto; }  
    public void setTexto(String texto) { this.texto = texto; }  
   
    @ManyToOne
    @JsonIgnoreProperties("postagens")
    private Tema tema;

    // Getter e Setter
    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
    
    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Usuario usuario;
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
