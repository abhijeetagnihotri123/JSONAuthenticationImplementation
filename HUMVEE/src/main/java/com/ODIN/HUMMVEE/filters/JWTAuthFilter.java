package com.ODIN.HUMMVEE.filters;

import com.ODIN.HUMMVEE.Service.CustomUserDetailsService;
import com.ODIN.HUMMVEE.utilities.JWTGenerationUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTGenerationUtility jwtGenerationUtility;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String usernameFromJWT = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
            usernameFromJWT = jwtGenerationUtility.extractUserName(jwtToken);
        }

        if(usernameFromJWT != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(usernameFromJWT);
            if(jwtGenerationUtility.validateToken(usernameFromJWT , userDetails , jwtToken)){
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken  = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
              usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request , response);
    }
}
