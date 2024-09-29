package br.com.carvalho.CarvalhoAtelieAdm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "blacklisted_token:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void blacklistToken(String token) {
        Date expirationDate = jwtTokenProvider.getExpirationDateFromToken(token);
        long expirationInSeconds = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;
        if (expirationInSeconds > 0) {
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "blacklisted", expirationInSeconds, TimeUnit.SECONDS);
        }
    }

    public boolean isBlacklisted(String token) {
        Boolean isBlacklisted = redisTemplate.hasKey(BLACKLIST_PREFIX + token);
        return Boolean.TRUE.equals(isBlacklisted);
    }
}