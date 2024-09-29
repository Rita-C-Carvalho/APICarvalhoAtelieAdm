package br.com.carvalho.CarvalhoAtelieAdm.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes para GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Deve tratar UsuarioNaoEncontradoException")
    void deveTratatUsuarioNaoEncontradoException() {
        UsuarioNaoEncontradoException ex = new UsuarioNaoEncontradoException("Usuário não encontrado");
        ResponseEntity<String> response = handler.handleUsuarioNaoEncontradoException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar UsuarioNaoCadastradoException")
    void deveTratatUsuarioNaoCadastradoException() {
        UsuarioNaoCadastradoException ex = new UsuarioNaoCadastradoException("Usuário não cadastrado");
        ResponseEntity<String> response = handler.handleUsuarioNaoCadastradoException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não cadastrado", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar UsuarioNaoAutenticadoException")
    void deveTratatUsuarioNaoAutenticadoException() {
        UsuarioNaoAutenticadoException ex = new UsuarioNaoAutenticadoException("Usuário não autenticado");
        ResponseEntity<String> response = handler.handleUsuarioNaoAutenticadoException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário não autenticado", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar SenhaIncorretaException")
    void deveTratatSenhaIncorretaException() {
        SenhaIncorretaException ex = new SenhaIncorretaException("Senha incorreta");
        ResponseEntity<String> response = handler.handleSenhaIncorretaException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Senha incorreta", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar TokenExpiradoException")
    void deveTratatTokenExpiradoException() {
        TokenExpiradoException ex = new TokenExpiradoException("Token expirado");
        ResponseEntity<String> response = handler.handleTokenExpiradoException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token expirado", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar TokenInvalidoException")
    void deveTratatTokenInvalidoException() {
        TokenInvalidoException ex = new TokenInvalidoException("Token inválido");
        ResponseEntity<String> response = handler.handleTokenInvalidoException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar IllegalStateException")
    void deveTratatIllegalStateException() {
        IllegalStateException ex = new IllegalStateException("Estado ilegal");
        ResponseEntity<String> response = handler.handleIllegalStateException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Estado ilegal", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar Exception genérica")
    void deveTratatExceptionGenerica() {
        Exception ex = new Exception("Erro genérico");
        ResponseEntity<String> response = handler.handleGenericException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor: Erro genérico", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar ProdutoNaoEncontradoException")
    void deveTratatProdutoNaoEncontradoException() {
        ProdutoNaoEncontradoException ex = new ProdutoNaoEncontradoException("Produto nao encontrado");
        ResponseEntity<String> response = handler.handleProdutoNaoEncontradoException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Produto nao encontrado", response.getBody());
    }
}