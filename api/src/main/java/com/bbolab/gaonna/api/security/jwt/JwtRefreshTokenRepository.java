package com.bbolab.gaonna.api.security.jwt;

import java.util.UUID;

public interface JwtRefreshTokenRepository {
    void saveRefreshToken(UUID userId, String token);
    String getRefreshToken(UUID userId, String token);
}
