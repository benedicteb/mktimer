package io.brkn.mktimer.web.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.brkn.mktimer.services.TokenAuthenticationService;
import io.brkn.mktimer.web.forms.LoginForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse resp)
            throws AuthenticationException, IOException {
        LoginForm creds = new ObjectMapper()
                .readValue(req.getInputStream(), LoginForm.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req, HttpServletResponse resp,
            FilterChain chain, Authentication auth) {
        TokenAuthenticationService
                .addAuthentication(resp, auth.getName());
    }
}
