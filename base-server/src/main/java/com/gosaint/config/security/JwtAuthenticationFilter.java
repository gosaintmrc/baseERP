package com.gosaint.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.debug("JWT Filter - Request: {} {}", request.getMethod(), request.getRequestURI());
            String jwt = getJwtFromRequest(request);

            if (jwt != null) {
                try {
                    String username = tokenProvider.getUsernameFromJWT(jwt);
                    log.debug("JWT parsed username: {}", username);
                    
                    // 先加载用户详情
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    log.debug("Loaded user details: {} with authorities: {}", userDetails.getUsername(), userDetails.getAuthorities());
                    
                    // 然后使用用户详情进行令牌验证
                    if (userDetails != null && tokenProvider.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.debug("Authentication set in context for user: {}", userDetails.getUsername());
                    } else {
                        log.warn("JWT validation failed for user: {}", username);
                    }
                } catch (Exception tokenEx) {
                    log.error("Invalid or expired JWT token", tokenEx);
                    // 不抛出异常，继续过滤器链
                }
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}