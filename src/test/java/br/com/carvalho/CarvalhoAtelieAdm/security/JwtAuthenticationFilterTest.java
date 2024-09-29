package br.com.carvalho.CarvalhoAtelieAdm.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para JwtAuthenticationFilter")
class JwtAuthenticationFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Nested
    @DisplayName("Testes para doFilterInternal")
    class DoFilterInternalTests {

        @Test
        @DisplayName("Deve autenticar usuário com token válido")
        void deveAutenticarUsuarioComTokenValido() throws ServletException, IOException {
            String token = "valid_token";
            String username = "user@example.com";
            UserDetails userDetails = mock(UserDetails.class);

            when(jwtTokenProvider.resolveToken(request)).thenReturn(token);
            when(jwtTokenProvider.validateToken(token)).thenReturn(true);
            when(jwtTokenProvider.getUsername(token)).thenReturn(username);
            when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain).doFilter(request, response);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assertNotNull(authentication);
            assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
            assertEquals(userDetails, authentication.getPrincipal());
        }

        @Test
        @DisplayName("Deve continuar a cadeia de filtros quando o token é nulo")
        void deveContinuarCadeiaFiltrosQuandoTokenNulo() throws ServletException, IOException {
            when(jwtTokenProvider.resolveToken(request)).thenReturn(null);

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }

        @Test
        @DisplayName("Deve lidar com token inválido")
        void deveLidarComTokenInvalido() throws ServletException, IOException {
            String token = "invalid_token";
            when(jwtTokenProvider.resolveToken(request)).thenReturn(token);
            when(jwtTokenProvider.validateToken(token)).thenReturn(false);

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }

        @Test
        @DisplayName("Deve lidar com exceção genérica")
        void deveLidarComExcecaoGenerica() throws ServletException, IOException {
            when(jwtTokenProvider.resolveToken(request)).thenThrow(new RuntimeException("Erro inesperado"));

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain).doFilter(request, response);
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        }
    }
}