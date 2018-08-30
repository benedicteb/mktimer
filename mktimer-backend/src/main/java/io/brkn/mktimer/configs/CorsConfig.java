package io.brkn.mktimer.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig {
    @Value("${mktimer.cors.allowedorigins}")
    private String corsAllowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/").allowedOrigins(corsAllowedOrigins);

                registry.addMapping("/login").allowedMethods("POST");

                registry.addMapping("/activity/start").allowedMethods("POST");
                registry.addMapping("/activity/stop").allowedMethods("POST");
                registry.addMapping("/activity").allowedMethods("GET", "POST");

                registry.addMapping("/category").allowedMethods("GET", "POST");
            }
        };
    }
}
