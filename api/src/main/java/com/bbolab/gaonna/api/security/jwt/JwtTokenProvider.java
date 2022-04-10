package com.bbolab.gaonna.api.security.jwt;

import com.bbolab.gaonna.api.config.SecurityProperties;
import com.bbolab.gaonna.api.security.model.JwtToken;
import com.bbolab.gaonna.api.security.model.UserPrincipal;
import io.jsonwebtoken.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final SecurityProperties securityProperties;
    private final UserDetailsService userDetailsService;
    private final JwtRefreshTokenRepository refreshTokenRepository;

    public JwtToken generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String accessToken = createAccessToken(userPrincipal);
        String refreshToken = createRefreshToken(userPrincipal);

        refreshTokenRepository.saveRefreshToken(userPrincipal.getUuid(), refreshToken);

        return new JwtToken(accessToken, refreshToken);
    }

    private String createAccessToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityProperties.getAuth().getAccessTokenExpireTime());

        return Jwts.builder()
                .setHeaderParam("typ", "ACCESS_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(userPrincipal.getUuid().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, securityProperties.getAuth().getTokenSecret())
                .compact();
    }

    private String createRefreshToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityProperties.getAuth().getRefreshTokenExpireTime());

        return Jwts.builder()
                .setHeaderParam("typ", "ACCESS_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(userPrincipal.getUuid().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, securityProperties.getAuth().getTokenSecret())
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(securityProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString(claims.getSubject());
    }

    public boolean validateToken(String authToken) throws JwtException {

            Jwts.parser().setSigningKey(securityProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;

//        return false;
    }

}
