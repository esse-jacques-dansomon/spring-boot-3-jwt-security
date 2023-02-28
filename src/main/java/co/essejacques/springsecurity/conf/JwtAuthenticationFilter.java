package co.essejacques.springsecurity.conf;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Author  : essejacques.co
 * Date    : 27/02/2023 22:19
 * Project : spring-security
 */


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        String header = request.getHeader("Authorization");
        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user accessing a public path or asking for a token.
        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.
        // 3. Get the token
        final String jwt, userEmail;
        jwt = header.substring(7);
        userEmail = jwtService.getUserEmailFromToken(jwt);
        // 4. validate the token
        try {
            // 5. check if the token is valid and not expired
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // if token is valid configure Spring Security to manually set authentication
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                // if token is valid configure Spring Security to manually set authentication
                // 6. if everything is valid, then set the authentication in the context, to specify that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                if (jwtService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // 7. continue filter execution
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            // 8. if the token is not valid, then send an error response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
        }

    }
}
