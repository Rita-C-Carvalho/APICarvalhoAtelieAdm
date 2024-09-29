package br.com.carvalho.CarvalhoAtelieAdm.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para TokenBlacklistService")
class TokenBlacklistServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private TokenBlacklistService tokenBlacklistService;

    private static final String BLACKLIST_PREFIX = "blacklisted_token:";

    @Nested
    @DisplayName("Testes para blacklistToken")
    class BlacklistTokenTests {

        @Test
        @DisplayName("Deve adicionar token válido à blacklist")
        void deveAdicionarTokenValidoABlacklist() {
            String token = "validToken";
            Date futureDate = new Date(System.currentTimeMillis() + 3600000);
            when(jwtTokenProvider.getExpirationDateFromToken(token)).thenReturn(futureDate);
            when(redisTemplate.opsForValue()).thenReturn(valueOperations);

            tokenBlacklistService.blacklistToken(token);

            verify(valueOperations).set(eq(BLACKLIST_PREFIX + token), eq("blacklisted"), anyLong(), eq(TimeUnit.SECONDS));
        }

        @Test
        @DisplayName("Não deve adicionar token expirado à blacklist")
        void naoDeveAdicionarTokenExpiradoABlacklist() {
            String token = "expiredToken";
            Date pastDate = new Date(System.currentTimeMillis() - 3600000);
            when(jwtTokenProvider.getExpirationDateFromToken(token)).thenReturn(pastDate);

            tokenBlacklistService.blacklistToken(token);

            verify(redisTemplate, never()).opsForValue();
        }
    }

    @Nested
    @DisplayName("Testes para isBlacklisted")
    class IsBlacklistedTests {

        @Test
        @DisplayName("Deve retornar true para token na blacklist")
        void deveRetornarTrueParaTokenNaBlacklist() {
            String token = "blacklistedToken";
            when(redisTemplate.hasKey(BLACKLIST_PREFIX + token)).thenReturn(true);

            boolean result = tokenBlacklistService.isBlacklisted(token);

            assertTrue(result);
        }

        @Test
        @DisplayName("Deve retornar false para token não na blacklist")
        void deveRetornarFalseParaTokenNaoNaBlacklist() {
            String token = "validToken";
            when(redisTemplate.hasKey(BLACKLIST_PREFIX + token)).thenReturn(false);

            boolean result = tokenBlacklistService.isBlacklisted(token);

            assertFalse(result);
        }

        @Test
        @DisplayName("Deve retornar false quando Redis retorna null")
        void deveRetornarFalseQuandoRedisRetornaNull() {
            String token = "nullToken";
            when(redisTemplate.hasKey(BLACKLIST_PREFIX + token)).thenReturn(null);

            boolean result = tokenBlacklistService.isBlacklisted(token);

            assertFalse(result);
        }
    }
}