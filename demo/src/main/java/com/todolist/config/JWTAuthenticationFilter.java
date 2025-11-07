//package com.todolist.config;
//
//import com.todolist.security.auth.jwt.JwtUtils;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
//@Component
//@RequiredArgsConstructor
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
//
//    private final JwtUtils jwtService;
//    private final UserDetailsService userDetailsService;
//
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");//we pass jwt authentication for authorization
//        final String jwt;
//        final String userEmail;
//        logger.info("Authenticating user with JWT token");
//        logger.info("Auth header: {}", authHeader);
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            logger.info("No JWT token found");
//            return;
//        }
//        jwt = authHeader.substring(7);
//        logger.info("JWT token: {}", jwt);
//        userEmail = jwtService.extractUsername(jwt);// extracting the userEmail from JWT token;
//        logger.info("User email: {}", userEmail);
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//                logger.info("JWT token is valid");
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                logger.info("Setting authentication for user: {}", userEmail);
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//        logger.info("Authentication filter completed");
//    }
//}
