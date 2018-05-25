package io.brkn.mktimer.web.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class AccessCodeFilter implements Filter {
    @Value("${mktimer.username}")
    private String username;

    @Value("${mktimer.password}")
    private String password;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("authorization");
        String expectedAuthHeader = "Basic " + Base64.getEncoder().encodeToString((this.username + ":" + this.password).getBytes("UTF-8"));

        if (authHeader == null || authHeader.isEmpty()) {
            response.sendError(401, "Unauthorized");
        } else if (!expectedAuthHeader.equals(authHeader)) {
            response.sendError(401, "Invalid token");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
