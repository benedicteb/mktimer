package io.brkn.mktimer.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    @Value("${mktimer.jwt.secret}")
    private String SECRET;

    @Value("${mktimer.jwt.expiration}")
    private long EXPIRATION;

    @Value("${mktimer.jwt.header}")
    private String HEADER_NAME;

    @Value("${mktimer.jwt.prefix}")
    private String TOKEN_PREFIX;

    public void addAuthentication(HttpServletResponse res, String username) {
        res.addHeader(HEADER_NAME,
                TOKEN_PREFIX + " " + generateJwtAccessToken(username));
    }

    public Authentication getAuthentication(HttpServletRequest request) throws SignatureException, ExpiredJwtException {
        String token = request.getHeader(HEADER_NAME);

        if (token == null || token.isEmpty()) {
            return null;
        }

        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return user != null ?
                new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                null;
    }

    public String generateJwtAccessToken(String username) {
        Date currentTime = new Date();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
