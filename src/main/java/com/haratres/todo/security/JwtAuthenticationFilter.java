package com.haratres.todo.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            String email = null;
            String authToken = tokenProvider.resolveToken(req);

            if (StringUtils.isNotBlank(authToken)) {
                email = tokenProvider.getEmailFromToken(authToken);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (tokenProvider.validateToken(authToken)) {
                    UsernamePasswordAuthenticationToken authentication =
                            tokenProvider.getAuthenticationToken(authToken, userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(req, res);
        } catch (RuntimeException ex) {
            res.setStatus(res.SC_BAD_REQUEST);
            res.setContentType("application/json");
            String json = "{\"message\":\"" + ex.getMessage() + "\"}";
            res.getWriter().write(json);
        }
    }
}