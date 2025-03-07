package com.generation.blogpessoal.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_temas")
public class Tema {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String descricao;

	    @OneToMany(mappedBy = "tema")
	    @JsonIgnoreProperties("tema")
	    private List<Postagem> postagens;

	    // Getters e Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	    public void setDescricao(String descricao) {
	        this.descricao = descricao;
	    }

	    public List<Postagem> getPostagens() {
	        return postagens;
	    }

	    public void setPostagens(List<Postagem> postagens) {
	        this.postagens = postagens;
	    }
	}

