package br.com.carvalho.CarvalhoAtelieAdm.security;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.factory.UsuarioFactory;
import br.com.carvalho.CarvalhoAtelieAdm.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para CustomUserDetailsService")
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = UsuarioFactory.createUser();
    }

    @Nested
    @DisplayName("Testes para loadUserByUsername")
    class LoadUserByUsernameTests {

        @Test
        @DisplayName("Deve retornar UserDetails quando o usuário for encontrado")
        void deveRetornarUserDetailsQuandoUsuarioEncontrado() {
            when(usuarioRepository.findByEmail(usuarioMock.getEmail())).thenReturn(Optional.of(usuarioMock));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(usuarioMock.getEmail());

            assertNotNull(userDetails);
            assertEquals(usuarioMock.getEmail(), userDetails.getUsername());
            assertEquals(usuarioMock.getSenha(), userDetails.getPassword());
            assertTrue(userDetails.getAuthorities().isEmpty());
        }

        @Test
        @DisplayName("Deve lançar UsernameNotFoundException quando o usuário não for encontrado")
        void deveLancarUsernameNotFoundExceptionQuandoUsuarioNaoEncontrado() {
            String emailInexistente = "email@inexistente.com";
            when(usuarioRepository.findByEmail(emailInexistente)).thenReturn(Optional.empty());

            UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                    () -> customUserDetailsService.loadUserByUsername(emailInexistente));

            assertEquals("Usuário não encontrado com o email: " + emailInexistente, exception.getMessage());
        }
    }
}