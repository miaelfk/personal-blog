package com.generation.blogpessoal.model;  

import jakarta.persistence.Entity;  
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;  
import jakarta.persistence.Id;  
import jakarta.persistence.Table;  

@Entity  
@Table(name = "tb_postagens")  
public class Postagem {  

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  

    private String titulo;  
    private String texto;  

    // Get e Set (não apague!)  
    public Long getId() { return id; }  
    public void setId(Long id) { this.id = id; }  

    public String getTitulo() { return titulo; }  
    public void setTitulo(String titulo) { this.titulo = titulo; }  

    public String getTexto() { return texto; }  
    public void setTexto(String texto) { this.texto = texto; }  
}  
