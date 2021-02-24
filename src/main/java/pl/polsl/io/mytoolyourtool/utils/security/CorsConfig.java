package pl.polsl.io.mytoolyourtool.utils.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/categories")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET","POST","DELETE")
                        .maxAge(3600);
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET","POST","DELETE")
                        .maxAge(3600);
            }
        };
    }
}
