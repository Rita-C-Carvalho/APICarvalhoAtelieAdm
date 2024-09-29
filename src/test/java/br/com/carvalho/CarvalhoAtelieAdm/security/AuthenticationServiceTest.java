package br.com.carvalho.CarvalhoAtelieAdm.security;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.LoginRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.LoginResponse;
import br.com.carvalho.CarvalhoAtelieAdm.exception.SenhaIncorretaException;
import br.com.carvalho.CarvalhoAtelieAdm.exception.UsuarioNaoCadastradoException;
import br.com.carvalho.CarvalhoAtelieAdm.service.usuario.BuscarUsuarioSecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para AuthenticationService")
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private BuscarUsuarioSecurityService buscarUsuarioSecurityService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Nested
    @DisplayName("Testes para authenticateUser")
    class AuthenticateUserTests {

        @Test
        @DisplayName("Deve autenticar usuário com credenciais válidas")
        void deveAutenticarUsuarioComCredenciaisValidas() {
            // Arrange
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail("user@example.com");
            loginRequest.setSenha("password");

            doNothing().when(buscarUsuarioSecurityService).buscarPorEmail(loginRequest.getEmail());

            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn(loginRequest.getEmail());
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

            when(jwtTokenProvider.createToken(loginRequest.getEmail())).thenReturn("valid_token");

            // Act
            LoginResponse response = authenticationService.authenticateUser(loginRequest);

            // Assert
            assertNotNull(response);
            assertEquals("valid_token", response.getToken());
            verify(buscarUsuarioSecurityService).buscarPorEmail(loginRequest.getEmail());
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(jwtTokenProvider).createToken(loginRequest.getEmail());
        }

        @Test
        @DisplayName("Deve lançar UsuarioNaoCadastradoException quando usuário não existe")
        void deveLancarUsuarioNaoCadastradoExceptionQuandoUsuarioNaoExiste() {
            // Arrange
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail("nonexistent@example.com");
            loginRequest.setSenha("password");

            doThrow(new UsuarioNaoCadastradoException("Usuário não cadastrado"))
                    .when(buscarUsuarioSecurityService).buscarPorEmail(loginRequest.getEmail());

            // Act & Assert
            assertThrows(UsuarioNaoCadastradoException.class, () -> authenticationService.authenticateUser(loginRequest));
            verify(buscarUsuarioSecurityService).buscarPorEmail(loginRequest.getEmail());
            verify(authenticationManager, never()).authenticate(any());
        }

        @Test
        @DisplayName("Deve lançar SenhaIncorretaException quando a senha está incorreta")
        void deveLancarSenhaIncorretaExceptionQuandoSenhaIncorreta() {
            // Arrange
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail("user@example.com");
            loginRequest.setSenha("wrong_password");

            doNothing().when(buscarUsuarioSecurityService).buscarPorEmail(loginRequest.getEmail());

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Credenciais inválidas"));

            // Act & Assert
            assertThrows(SenhaIncorretaException.class, () -> authenticationService.authenticateUser(loginRequest));
            verify(buscarUsuarioSecurityService).buscarPorEmail(loginRequest.getEmail());
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        }
    }
}