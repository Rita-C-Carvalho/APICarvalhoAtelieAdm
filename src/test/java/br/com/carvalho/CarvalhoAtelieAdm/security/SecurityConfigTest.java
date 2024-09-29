package br.com.carvalho.CarvalhoAtelieAdm.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para SecurityConfig")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private ApplicationContext applicationContext;

    @Nested
    @DisplayName("Testes de configuração de beans")
    class BeanConfigurationTests {

        @Test
        @DisplayName("Deve criar um bean PasswordEncoder")
        void deveCriarBeanPasswordEncoder() {
            PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
            assertNotNull(passwordEncoder);
            assertTrue(passwordEncoder instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder);
        }


        @Test
        @DisplayName("Deve criar um bean AuthenticationManager")
        void deveCriarBeanAuthenticationManager() {
            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
            assertNotNull(authenticationManager);
        }
    }

    @Nested
    @DisplayName("Testes de configuração de segurança")
    class SecurityConfigurationTests {

        @Test
        @DisplayName("Deve negar acesso a endpoints protegidos sem autenticação")
        void deveNegarAcessoEndpointsProtegidosSemAutenticacao() throws Exception {
            mockMvc.perform(get("/api/protected"))
                    .andExpect(status().isUnauthorized());
        }
    }
}