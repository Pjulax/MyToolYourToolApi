package pl.polsl.io.mytoolyourtool.utils.security.jwt;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        if(token!=null) {
            try {
                if (jwtTokenProvider.validateToken(token)) {
                    Authentication auth = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JwtException | UsernameNotFoundException ex) {
                SecurityContextHolder.clearContext();
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                httpServletResponse.setContentType("text/plain");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(ex.getMessage());
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
