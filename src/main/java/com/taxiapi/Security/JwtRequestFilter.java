package com.taxiapi.Security;

import com.taxiapi.Exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Profile("production")
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsManagerService userDetailsService;


    public JwtRequestFilter(JwtUtil jwtUtil,
                            UserDetailsManagerService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        
        String path = request.getRequestURI();
        
        if (path.equals("/api/admin/login") ||
                path.equals("/api/admin/register") ||
                path.startsWith("/api/users/routes")) {
            filterChain.doFilter(request, response);
            return;
        }

        try{
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            } else {
                throw new UnauthorizedException("Authorization" +
                        " token is missing.");
            }

            if(username != null && SecurityContextHolder.getContext()
                    .getAuthentication() == null){
                UserDetails userDetails = userDetailsService
                        .loadUserByUsername(username);

                if (jwtUtil.isTokenValid(jwt,userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new
                            WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken);
                } else {
                    throw new UnauthorizedException("Your session has expired or is invalid." +
                            " Please try to log in again.");
                }
            }

            filterChain.doFilter(request, response);

        }catch(UnauthorizedException ex){
            throw new UnauthorizedException("Missing, Expired or Invalid token." +
                    " Please try logging in again.");
        }





    }
}
