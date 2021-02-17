package pl.polsl.io.mytoolyourtool.utils.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.io.mytoolyourtool.utils.security.jwt.JwtTokenFilterConfigurer;
import pl.polsl.io.mytoolyourtool.utils.security.jwt.JwtTokenProvider;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // CORS settings and CSRF disable
        http.cors(Customizer.withDefaults())
                .csrf().disable();
        // HTTP xss headers problem resolver for localhost
        http.headers()
                .frameOptions()
                .sameOrigin();
                // .headers().frameOptions().disable()
        // Set all entries without and with authorization here
        http.authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/swagger-resources/configuration/ui").permitAll()
                .antMatchers("/swagger-resources/configuration/security").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();
        // Set session management to Stateless -> REST obligatory
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Disable unused logout and login form
        http.logout().disable()
                .formLogin().disable();
        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/api/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
