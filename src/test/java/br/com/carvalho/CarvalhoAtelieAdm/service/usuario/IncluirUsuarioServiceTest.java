package br.com.carvalho.CarvalhoAtelieAdm.service.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.UsuarioRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.factory.UsuarioFactory;
import br.com.carvalho.CarvalhoAtelieAdm.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// Esta anotação habilita o uso de mocks do Mockito no JUnit 5
class IncluirUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    // Cria um mock do UsuarioRepository

    @Mock
    private PasswordEncoder passwordEncoder;
    // Cria um mock do PasswordEncoder

    @InjectMocks
    private IncluirUsuarioService incluirUsuarioService;
    // Cria uma instância de IncluirUsuarioService e injeta os mocks nela

    @Test
    @DisplayName("Deve criar um novo usuário e retornar UserResponse")
        // Anotações para marcar o método como um teste e fornecer uma descrição legível
    void criarUsuario() {
        // Arrange (Preparação)
        Usuario usuarioMock = UsuarioFactory.createUser();
        // Cria um usuário mock usando a UsuarioFactory

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNomeCompleto(usuarioMock.getNomeCompleto());
        usuarioRequest.setEmail(usuarioMock.getEmail());
        usuarioRequest.setSenha(usuarioMock.getSenha());
        usuarioRequest.setImagemPerfil(usuarioMock.getImagemPerfil());
        // Cria um UsuarioRequest com os dados do usuário mock

        when(passwordEncoder.encode(usuarioRequest.getSenha())).thenReturn("senhaEncriptada");
        // Configura o mock do passwordEncoder para retornar "senhaEncriptada" quando encode for chamado

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);
        // Configura o mock do usuarioRepository para retornar o usuarioMock quando save for chamado

        // Act (Ação)
        UsuarioResponse usuarioResponse = incluirUsuarioService.criarUsuario(usuarioRequest);
        // Chama o método que está sendo testado

        // Assert (Verificação)
        assertNotNull(usuarioResponse);
        // Verifica se a resposta não é nula

        assertEquals(usuarioMock.getId(), usuarioResponse.getId());
        assertEquals(usuarioMock.getNomeCompleto(), usuarioResponse.getNomeCompleto());
        assertEquals(usuarioMock.getEmail(), usuarioResponse.getEmail());
        // Verifica se os dados da resposta correspondem aos do usuário mock

        verify(passwordEncoder).encode(usuarioRequest.getSenha());
        // Verifica se o método encode do passwordEncoder foi chamado com a senha correta

        verify(usuarioRepository).save(any(Usuario.class));
        // Verifica se o método save do usuarioRepository foi chamado com qualquer objeto Usuario
    }
}