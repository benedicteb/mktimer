package io.brkn.mktimer.web.filters;

import io.brkn.mktimer.services.BasicAuthenticationService;
import io.brkn.mktimer.services.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class BasicOrTokenAuthenticationFilter implements Filter {
    private BasicAuthenticationService basicAuthenticationService;
    private TokenAuthenticationService tokenAuthenticationService;

    public BasicOrTokenAuthenticationFilter(BasicAuthenticationService basicAuthenticationService,
                                            TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.basicAuthenticationService = basicAuthenticationService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        try {
            Authentication authentication;

            authentication = basicAuthenticationService.getAuthentication(request);

            // Basic auth failed - try JWT
            if (authentication == null) {
                authentication = tokenAuthenticationService.getAuthentication(request);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, resp);
        } catch (SignatureException signatureException) {
            response.sendError(401, "Unauthorized");
        } catch (MalformedJwtException malformedException) {
            response.sendError(401, "Malformed JWT token");
        } catch (ExpiredJwtException expiredJwtException) {
            response.sendError(401, "JWT token expired");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
