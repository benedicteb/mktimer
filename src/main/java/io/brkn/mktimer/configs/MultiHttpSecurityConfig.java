package io.brkn.mktimer.configs;

import io.brkn.mktimer.services.BasicAuthenticationService;
import io.brkn.mktimer.services.TokenAuthenticationService;
import io.brkn.mktimer.web.filters.BasicOrTokenAuthenticationFilter;
import io.brkn.mktimer.web.filters.JWTAuthenticationFilter;
import io.brkn.mktimer.web.filters.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {
    @Value("${mktimer.username}")
    private String username;

    @Value("${mktimer.password}")
    private String password;

    @Value("${mktimer.roles}")
    private String roles;

    /*
     * Since order is 1, this will be run first.
     */
    @Configuration
    @Order(1)
    public class BasicSecurityConfigurationManager extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                        .disable()
                    .antMatcher("/activity/*")
                        .authorizeRequests()
                            .anyRequest()
                            .authenticated()
                    .and()
                        .addFilterBefore(new BasicOrTokenAuthenticationFilter(basicAuthenticationService(),
                                tokenAuthenticationService()),
                                UsernamePasswordAuthenticationFilter.class);
        }
    }

    /*
     * Since no order is set - this will be run last.
     */
    @Configuration
    public class JWTSecurityConfigurationManager extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                        .disable()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(),
                                        tokenAuthenticationService()),
                                UsernamePasswordAuthenticationFilter.class)
                        .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService()),
                                UsernamePasswordAuthenticationFilter.class);

        }
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(username)
                .password(passwordEncoder().encode(password))
                .roles(roles);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService();
    }

    @Bean
    public BasicAuthenticationService basicAuthenticationService() {
        return new BasicAuthenticationService();
    }
}
