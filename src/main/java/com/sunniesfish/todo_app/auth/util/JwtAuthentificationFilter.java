package com.sunniesfish.todo_app.auth.util;

import com.sunniesfish.todo_app.auth.dto.CustomUserDetails;
import com.sunniesfish.todo_app.auth.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        System.out.println("doFilterInternal---------------------------------------------------- ");
        System.out.println("dofilter internal ======================== token: " + token);
        String username = null;
        if (token != null) {
            username = jwtUtil.getUsernameFormAccessToken(token);
            System.out.println("username is " + username);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            UserDetails userDetails = new CustomUserDetails(username, authorities);

            if (jwtUtil.validateAccessToken(token, userDetails.getUsername())){
                JwtAuthentificationToken authentication = new JwtAuthentificationToken(
                        userDetails, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("authentication token is " + authentication.getName());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            System.out.println("get token from Request-------------------------- bearerToken is " + bearerToken);
            return bearerToken.substring(7);
        }
        return null;
    }
}
