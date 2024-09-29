package br.com.carvalho.CarvalhoAtelieAdm.service.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.exception.UsuarioNaoCadastradoException;
import br.com.carvalho.CarvalhoAtelieAdm.exception.UsuarioNaoEncontradoException;
import br.com.carvalho.CarvalhoAtelieAdm.factory.UsuarioFactory;
import br.com.carvalho.CarvalhoAtelieAdm.repository.UsuarioRepository;
import br.com.carvalho.CarvalhoAtelieAdm.security.util.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioSecurityServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private BuscarUsuarioSecurityService buscarUsuarioSecurityService;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = UsuarioFactory.createUser();
    }

    @Nested
    @DisplayName("Testes para o método buscar")
    class BuscarTests {

        @Test
        @DisplayName("Deve retornar UsuarioResponse quando o usuário for encontrado")
        void deveRetornarUsuarioResponseQuandoUsuarioEncontrado() {
            try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
                securityUtils.when(SecurityUtils::getEmailUsuarioLogado).thenReturn(usuarioMock.getEmail());
                when(usuarioRepository.findByEmail(usuarioMock.getEmail())).thenReturn(Optional.of(usuarioMock));

                UsuarioResponse response = buscarUsuarioSecurityService.buscar();

                assertNotNull(response);
                assertEquals(usuarioMock.getEmail(), response.getEmail());
                assertEquals(usuarioMock.getNomeCompleto(), response.getNomeCompleto());
            }
        }

        @Test
        @DisplayName("Deve lançar UsuarioNaoEncontradoException quando o usuário não for encontrado")
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
                securityUtils.when(SecurityUtils::getEmailUsuarioLogado).thenReturn("email@inexistente.com");
                when(usuarioRepository.findByEmail("email@inexistente.com")).thenReturn(Optional.empty());

                assertThrows(UsuarioNaoEncontradoException.class, () -> buscarUsuarioSecurityService.buscar());
            }
        }
    }

    @Nested
    @DisplayName("Testes para o método buscarUsuario")
    class BuscarUsuarioTests {

        @Test
        @DisplayName("Deve retornar Usuario quando o usuário for encontrado")
        void deveRetornarUsuarioQuandoUsuarioEncontrado() {
            try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
                securityUtils.when(SecurityUtils::getEmailUsuarioLogado).thenReturn(usuarioMock.getEmail());
                when(usuarioRepository.findByEmail(usuarioMock.getEmail())).thenReturn(Optional.of(usuarioMock));

                Usuario usuario = buscarUsuarioSecurityService.buscarUsuario();

                assertNotNull(usuario);
                assertEquals(usuarioMock.getEmail(), usuario.getEmail());
                assertEquals(usuarioMock.getNomeCompleto(), usuario.getNomeCompleto());
            }
        }

        @Test
        @DisplayName("Deve lançar UsuarioNaoEncontradoException quando o usuário não for encontrado")
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
                securityUtils.when(SecurityUtils::getEmailUsuarioLogado).thenReturn("email@inexistente.com");
                when(usuarioRepository.findByEmail("email@inexistente.com")).thenReturn(Optional.empty());

                assertThrows(UsuarioNaoEncontradoException.class, () -> buscarUsuarioSecurityService.buscarUsuario());
            }
        }
    }

    @Nested
    @DisplayName("Testes para o método buscarPorEmail")
    class BuscarPorEmailTests {

        @Test
        @DisplayName("Não deve lançar exceção quando o usuário existir")
        void naoDeveLancarExcecaoQuandoUsuarioExistir() {
            when(usuarioRepository.existsByEmail(usuarioMock.getEmail())).thenReturn(true);

            assertDoesNotThrow(() -> buscarUsuarioSecurityService.buscarPorEmail(usuarioMock.getEmail()));
        }

        @Test
        @DisplayName("Deve lançar UsuarioNaoCadastradoException quando o usuário não existir")
        void deveLancarExcecaoQuandoUsuarioNaoExistir() {
            when(usuarioRepository.existsByEmail("email@inexistente.com")).thenReturn(false);

            assertThrows(UsuarioNaoCadastradoException.class, () ->
                    buscarUsuarioSecurityService.buscarPorEmail("email@inexistente.com"));
        }
    }
}

/*
* A anotação @Nested é uma funcionalidade do JUnit 5 que permite criar grupos de testes aninhados
* dentro de uma classe de teste.
*  Ela serve para vários propósitos importantes:

Organização lógica: Permite agrupar testes relacionados, melhorando a estrutura e a legibilidade
* do código de teste.

Contexto compartilhado: Classes aninhadas podem compartilhar configurações e estado com a classe
* externa, permitindo um setup comum para um grupo de testes.

Hierarquia de testes: Cria uma hierarquia clara de testes, que é refletida nos relatórios de teste
*  e na saída da execução.

Encapsulamento: Ajuda a encapsular testes específicos para determinados cenários ou funcionalidades.

Reutilização de código: Permite reutilizar configurações e teardowns para grupos específicos
* de testes.

Melhora a legibilidade: Torna mais fácil entender a estrutura e o propósito dos testes,
* especialmente em classes de teste grandes.
 */