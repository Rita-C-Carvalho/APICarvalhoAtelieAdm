package br.com.carvalho.CarvalhoAtelieAdm.security;

import br.com.carvalho.CarvalhoAtelieAdm.exception.TokenExpiradoException;
import br.com.carvalho.CarvalhoAtelieAdm.exception.TokenInvalidoException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para JwtTokenProvider")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider("testSecretKeyWithAtLeast256BitsForHS256Algorithm");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 3600000L);
    }

    @Nested
    @DisplayName("Testes para createToken")
    class CreateTokenTests {

        @Test
        @DisplayName("Deve criar um token válido")
        void deveCriarTokenValido() {
            String username = "testuser";
            String token = jwtTokenProvider.createToken(username);

            assertNotNull(token);
            assertTrue(jwtTokenProvider.validateToken(token));
            assertEquals(username, jwtTokenProvider.getUsername(token));
        }
    }

    @Nested
    @DisplayName("Testes para getUsername")
    class GetUsernameTests {

        @Test
        @DisplayName("Deve retornar o username de um token válido")
        void deveRetornarUsernameDeTokenValido() {
            String username = "testuser";
            String token = jwtTokenProvider.createToken(username);

            assertEquals(username, jwtTokenProvider.getUsername(token));
        }

        @Test
        @DisplayName("Deve retornar null para um token inválido")
        void deveRetornarNullParaTokenInvalido() {
            String invalidToken = "invalidToken";

            assertNull(jwtTokenProvider.getUsername(invalidToken));
        }
    }

    @Nested
    @DisplayName("Testes para resolveToken")
    class ResolveTokenTests {

        @Test
        @DisplayName("Deve resolver o token corretamente do cabeçalho Authorization")
        void deveResolverTokenCorretamente() {
            String token = "validToken";
            when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

            assertEquals(token, jwtTokenProvider.resolveToken(request));
        }

        @Test
        @DisplayName("Deve retornar null quando não há token no cabeçalho")
        void deveRetornarNullQuandoNaoHaToken() {
            when(request.getHeader("Authorization")).thenReturn(null);

            assertNull(jwtTokenProvider.resolveToken(request));
        }

        @Test
        @DisplayName("Deve retornar null quando o formato do token é inválido")
        void deveRetornarNullQuandoFormatoTokenInvalido() {
            when(request.getHeader("Authorization")).thenReturn("InvalidFormat token");

            assertNull(jwtTokenProvider.resolveToken(request));
        }
    }

    @Nested
    @DisplayName("Testes para validateToken")
    class ValidateTokenTests {

        @Test
        @DisplayName("Deve validar um token válido")
        void deveValidarTokenValido() {
            String token = jwtTokenProvider.createToken("testuser");

            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Deve lançar TokenExpiradoException para um token expirado")
        void deveLancarTokenExpiradoException() {
            String expiredToken = Jwts.builder()
                    .setSubject("testuser")
                    .setIssuedAt(new Date(System.currentTimeMillis() - 3600000))
                    .setExpiration(new Date(System.currentTimeMillis() - 1000))
                    .signWith((Key) ReflectionTestUtils.getField(jwtTokenProvider, "secretKey"))
                    .compact();

            assertThrows(TokenExpiradoException.class, () -> jwtTokenProvider.validateToken(expiredToken));
        }

        @Test
        @DisplayName("Deve lançar TokenInvalidoException para um token inválido")
        void deveLancarTokenInvalidoException() {
            String invalidToken = "invalidToken";

            assertThrows(TokenInvalidoException.class, () -> jwtTokenProvider.validateToken(invalidToken));
        }
    }

    @Nested
    @DisplayName("Testes para getExpirationDateFromToken")
    class GetExpirationDateFromTokenTests {

        @Test
        @DisplayName("Deve retornar a data de expiração correta para um token válido")
        void deveRetornarDataExpiracaoCorreta() {
            String username = "testuser";
            String token = jwtTokenProvider.createToken(username);

            Date expirationDate = jwtTokenProvider.getExpirationDateFromToken(token);

            assertNotNull(expirationDate);
            assertTrue(expirationDate.after(new Date()));
            assertTrue(expirationDate.before(new Date(System.currentTimeMillis() + 3600000L)));
        }

        @Test
        @DisplayName("Deve lançar TokenInvalidoException para um token inválido")
        void deveLancarTokenInvalidoExceptionParaTokenInvalido() {
            String invalidToken = "invalidToken";

            assertThrows(TokenInvalidoException.class, () -> jwtTokenProvider.getExpirationDateFromToken(invalidToken));
        }

        @Test
        @DisplayName("Deve lançar TokenInvalidoException para um token expirado")
        void deveLancarTokenInvalidoExceptionParaTokenExpirado() {
            String expiredToken = Jwts.builder()
                    .setSubject("testuser")
                    .setIssuedAt(new Date(System.currentTimeMillis() - 3600000))
                    .setExpiration(new Date(System.currentTimeMillis() - 1000))
                    .signWith((Key) ReflectionTestUtils.getField(jwtTokenProvider, "secretKey"))
                    .compact();

            assertThrows(TokenInvalidoException.class, () -> jwtTokenProvider.getExpirationDateFromToken(expiredToken));
        }
    }
}