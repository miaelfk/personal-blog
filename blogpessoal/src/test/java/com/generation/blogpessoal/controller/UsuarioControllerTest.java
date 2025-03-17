package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();

        // ordem correta: id, nome, usuario (email), senha, foto
        Usuario usuario = new Usuario();
        usuario.setNome("Root");
        usuario.setUsuario("root@root.com");
        usuario.setSenha("rootroot");
        usuario.setFoto("");
        
        usuarioService.cadastrarUsuario(usuario);
    }

    @Test
    @Order(1)
    @DisplayName("Cadastrar Um Usuário")
    public void deveCriarUmUsuario() {
        // criar objeto Usuario usando setters para evitar problemas com a ordem dos parâmetros
        Usuario usuario = new Usuario();
        usuario.setNome("Liam França");
        usuario.setUsuario("liamfranca@lilico.com.br");
        usuario.setSenha("13465278");
        usuario.setFoto("");
        
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuario);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
    }

    @Test
    @Order(2)
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {
        // criar primeiro usuário
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Sâmia França");
        usuario1.setUsuario("samiafranca@miazinha.com.br");
        usuario1.setSenha("13465278");
        usuario1.setFoto("");
        
        usuarioService.cadastrarUsuario(usuario1);

        // tentar criar usuário duplicado
        Usuario usuario2 = new Usuario();
        usuario2.setNome("Sâmia França");
        usuario2.setUsuario("samiafranca@miazinha.com.br");
        usuario2.setSenha("13465278");
        usuario2.setFoto("");
        
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuario2);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {
        // criar usuário inicial
        Usuario usuarioInicial = new Usuario();
        usuarioInicial.setNome("Miqueias França");
        usuarioInicial.setUsuario("miqueiasfranca@franca.com.br");
        usuarioInicial.setSenha("miq1957");
        usuarioInicial.setFoto("");
        
        Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(usuarioInicial);

        // criar usuário atualizado
        Usuario usuarioUpdate = new Usuario();
        usuarioUpdate.setId(usuarioCreate.get().getId());
        usuarioUpdate.setNome("Miqueias França");
        usuarioUpdate.setUsuario("miqueiasfranca@franca.com.br");
        usuarioUpdate.setSenha("miq1957");
        usuarioUpdate.setFoto("");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
    }

    @Test
    @Order(4)
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {
        // criar primeiro usuário adicional
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Luan França");
        usuario1.setUsuario("luanfranca@luluzinho.com.br");
        usuario1.setSenha("13465278");
        usuario1.setFoto("");
        
        usuarioService.cadastrarUsuario(usuario1);

        // criar segundo usuário adicional
        Usuario usuario2 = new Usuario();
        usuario2.setNome("Hina França");
        usuario2.setUsuario("hinafranca@hinazinha.com.br");
        usuario2.setSenha("hina123");
        usuario2.setFoto("");
        
        usuarioService.cadastrarUsuario(usuario2);

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}
