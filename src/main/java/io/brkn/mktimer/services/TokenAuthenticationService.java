package io.brkn.mktimer.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_NAME, TOKEN_PREFIX + " " + jwt);
    }

    public Authentication getAuthentication(HttpServletRequest request) throws SignatureException {
        String token = request.getHeader(HEADER_NAME);

        if (token == null || token.isEmpty()) {
            return null;
        }

        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return user != null?
                new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                null;
    }
}
