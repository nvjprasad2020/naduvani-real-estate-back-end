package com.naduvani.realestate.security.config;

import com.naduvani.realestate.exception.AppException;
import com.naduvani.realestate.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;
        final String userEmail;
        if (header != null) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    jwtToken = authElements[1];
                    userEmail = jwtService.extractUsername(jwtToken);
                    if (null != userEmail && null == SecurityContextHolder.getContext().getAuthentication()) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                        if (jwtService.isTokenValid(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails,
                                            null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw new AppException(HttpStatus.FORBIDDEN, "Error : "+ e.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
