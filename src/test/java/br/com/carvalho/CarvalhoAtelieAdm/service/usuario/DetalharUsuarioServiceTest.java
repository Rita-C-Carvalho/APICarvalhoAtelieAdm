package br.com.carvalho.CarvalhoAtelieAdm.service.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.DetalharUsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.factory.UsuarioFactory;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario.DetalharUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalharUsuarioServiceTest {

    @Mock
    private BuscarUsuarioSecurityService buscarUsuarioSecurityService;
    // Cria um mock do BuscarUsuarioSecurityService

    @InjectMocks
    private DetalharUsuarioService detalharUsuarioService;
    // Injeta o mock criado acima no DetalharUsuarioService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inicializa os mocks antes de cada teste
    }

    @Test
    @DisplayName("Deve detalhar usuário com sucesso")
        // Fornece um nome legível para o teste
    void detalharUsuario_deveRetornarDetalhesDoUsuario() {
        // Arrange
        Usuario usuarioMock = UsuarioFactory.createUser();
        // Cria um usuário mock usando a factory

        when(buscarUsuarioSecurityService.buscarUsuario()).thenReturn(usuarioMock);
        // Configura o mock para retornar o usuário mock quando buscarUsuario() for chamado

        DetalharUsuarioResponse expectedResponse = DetalharUsuarioMapper.toResponse(usuarioMock);
        // Cria a resposta esperada usando o mapper

        // Act
        DetalharUsuarioResponse actualResponse = detalharUsuarioService.detalharUsuario();
        // Chama o método que está sendo testado

        // Assert
        assertNotNull(actualResponse);
        // Verifica se a resposta não é nula

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getNomeCompleto(), actualResponse.getNomeCompleto());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getImagemPerfil(), actualResponse.getImagemPerfil());
        // Verifica se todos os campos da resposta correspondem ao esperado

        verify(buscarUsuarioSecurityService, times(1)).buscarUsuario();
        // Verifica se o método buscarUsuario() foi chamado exatamente uma vez
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
        // Fornece um nome legível para o teste de cenário de erro
    void detalharUsuario_deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(buscarUsuarioSecurityService.buscarUsuario())
                .thenThrow(new IllegalStateException("Usuário não autenticado"));
        // Configura o mock para lançar uma exceção quando buscarUsuario() for chamado

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            detalharUsuarioService.detalharUsuario();
        });
        // Verifica se a exceção esperada é lançada quando o método é chamado

        assertEquals("Usuário não autenticado", exception.getMessage());
        // Verifica se a mensagem da exceção está correta

        verify(buscarUsuarioSecurityService, times(1)).buscarUsuario();
        // Verifica se o método buscarUsuario() foi chamado exatamente uma vez
    }
}