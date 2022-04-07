package com.bbolab.gaonna.api.security;

import com.bbolab.gaonna.api.config.SecurityProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public String createAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

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

    public String createRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

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

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(securityProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.error("JWT RefreshToken is empty");
        }
        return false;
    }

}
