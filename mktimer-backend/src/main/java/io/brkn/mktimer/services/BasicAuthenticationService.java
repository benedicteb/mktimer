package io.brkn.mktimer.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BasicAuthenticationService {
    @Value("${mktimer.username}")
    private String username;

    @Value("${mktimer.password}")
    private String password;

    public Authentication getAuthentication(HttpServletRequest request) throws SignatureException,
            ExpiredJwtException, UnsupportedEncodingException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty()) {
            return null;
        }

        String expectedAuthHeader = "Basic " +
                Base64.getEncoder().encodeToString((username + ":" + password).getBytes("UTF-8"));

        if (!expectedAuthHeader.equals(authHeader)) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(username, null, emptyList());
    }
}
