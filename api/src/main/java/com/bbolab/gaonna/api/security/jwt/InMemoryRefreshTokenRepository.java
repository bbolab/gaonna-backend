package com.bbolab.gaonna.api.security.jwt;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO : blupine, we should implement RedisRefreshTokenRepository
@Repository
public class InMemoryRefreshTokenRepository implements JwtRefreshTokenRepository {

    Map<UUID, String> db = new HashMap<>();

    @Override
    public void saveRefreshToken(UUID userId, String token) {
        db.put(userId, token);
    }

    @Override
    public String getRefreshToken(UUID userId, String token) {
        return db.getOrDefault(userId, null);
    }
}
