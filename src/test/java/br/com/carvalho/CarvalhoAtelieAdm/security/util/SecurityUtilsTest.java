package br.com.carvalho.CarvalhoAtelieAdm.security.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para SecurityUtils")
class SecurityUtilsTest {

    @Nested
    @DisplayName("Testes para getEmailUsuarioLogado")
    class GetEmailUsuarioLogadoTests {

        @Test
        @DisplayName("Deve retornar email quando o principal é um UserDetails")
        void deveRetornarEmailQuandoPrincipalEUserDetails() {
            String expectedEmail = "usuario@teste.com";
            UserDetails userDetails = User.withUsername(expectedEmail)
                    .password("password")
                    .authorities(Collections.emptyList())
                    .build();

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(auth);

            try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
                securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

                String result = SecurityUtils.getEmailUsuarioLogado();

                assertEquals(expectedEmail, result);
            }
        }

        @Test
        @DisplayName("Deve retornar string quando o principal não é um UserDetails")
        void deveRetornarStringQuandoPrincipalNaoEUserDetails() {
            String expectedPrincipal = "anonymousUser";
            Authentication auth = new UsernamePasswordAuthenticationToken(expectedPrincipal, null);
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(auth);

            try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
                securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

                String result = SecurityUtils.getEmailUsuarioLogado();

                assertEquals(expectedPrincipal, result);
            }
        }
    }
}