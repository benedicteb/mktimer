package io.brkn.mktimer.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    private static long EXPIRATION = 172_800_000;
    private static String SECRET = "f0719f162ce0158b4c30e8a4f0781ee12a402a73";
    private static String HEADER_NAME = "Authorization";
    private static String TOKEN_PREFIX = "Bearer";

    public static void addAuthentication(HttpServletResponse res, String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_NAME, TOKEN_PREFIX + " " + jwt);
    }

    public static Authentication getAuthentication(HttpServletRequest request) throws SignatureException {
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
