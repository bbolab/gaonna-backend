package com.bbolab.gaonna.api.security.jwt;

import com.bbolab.gaonna.api.security.CustomUserDetailsService;
import com.bbolab.gaonna.api.security.model.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.tokenProvider = jwtTokenProvider;
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        try {
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                UUID userId = tokenProvider.getUserIdFromToken(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (SignatureException | MalformedJwtException ex) {
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getErrorCode());
        } catch (ExpiredJwtException ex) {
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getErrorCode());
        } catch (UnsupportedJwtException ex) {
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN.getErrorCode());
        } catch (IllegalArgumentException ex) {
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getErrorCode());
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getErrorCode());
            log.error("JWT RefreshToken is empty");
        } catch (Exception ex) {
            request.setAttribute("exception", ErrorCode.UNKNOWN_ERROR.getErrorCode());
            log.error("================================================");
            log.error("JwtTokenAuthenticationFilter - doFilterInternal() 오류발생");
            log.error("token : {}", jwt);
            log.error("Exception Message : {}", ex.getMessage());
            log.error("Exception StackTrace : {");
            ex.printStackTrace();
            log.error("}");
            log.error("================================================");
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
