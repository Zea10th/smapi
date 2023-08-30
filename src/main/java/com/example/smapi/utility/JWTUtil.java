package com.example.smapi.utility;

import com.example.smapi.configuration.JWTConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey SECRET;
    private final long EXPIRATION_TIME;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    Logger logger = LogManager.getLogger(JWTUtil.class);

    public JWTUtil(JWTConfig jwtUtil) {
        this.SECRET = Keys.hmacShaKeyFor(jwtUtil.getSecret().getBytes());
        this.EXPIRATION_TIME = jwtUtil.getExpirationTime();
    }

    public String generateAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + EXPIRATION_TIME);

        logger.info("JWTUtil.class -> Token to client with username %s has been generated.", user.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SECRET)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build();
        Claims body = parser.parseClaimsJws(token).getBody();

        logger.info("JWTUtil.class -> Claims are parsed: " + body);
        return body;
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if (token != null) {

                logger.info("JWTUtil.class -> Claims extracted from headers.");
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException e) {
            request.setAttribute("expired", e.getMessage());
            throw e;
        } catch (Exception ex) {
            request.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            logger.info("JWTUtil.class -> Token is expired. Claims are: " + claims);
            throw new NullPointerException();
        }
    }
}
