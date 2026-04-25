package com.bootcamp.jwt_and_refresh_token_authentication.application.security;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.CookieService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.impl.JwtTokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenServiceImpl jwtService;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Token'ı Cookie'den alıyoruz
        String jwt = cookieService.getJwtFromCookies(request);

        if (jwt != null && jwtService.validateToken(jwt)) {
            String username = jwtService.getUsernameFromToken(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Token içindeki rolleri okuyoruz
                List<String> roles = jwtService.getUserRolesFromToken(jwt);
                
                // Spring Security'nin anlayacağı GrantedAuthority nesnelerine (Spring standardı gereği başlarına ROLE_ eklenir) çeviriyoruz.
                List<SimpleGrantedAuthority> authorities = roles != null ? 
                        roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()) 
                        : List.of();

                // UserDetails nesnesini dinamik yetkilerle oluşturuyoruz
                UserDetails userDetails = new User(username, "", authorities);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}