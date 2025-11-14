package com.todolist.security.auth.services;


import com.todolist.model.User;
import com.todolist.repository.RoleRepository;
import com.todolist.repository.UserRepository;
import com.todolist.security.auth.AuthenticationRequest;
import com.todolist.security.auth.AuthenticationResponse;
import com.todolist.security.auth.RegisterRequest;
import com.todolist.security.auth.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.todolist.model.roleName;
import com.todolist.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

        public AuthenticationResponse register(RegisterRequest request) {

            Role role = roleRepository.findByRoleName(roleName.USER)
                    .orElseGet(() -> roleRepository.save(new Role(roleName.USER)));
            
            logger.info("User Role: {}", role);
            User user = new User();

            user.setRole(role);
            user.setName(request.getName());

            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            logger.info("User Detailed Information: {}", user);
            var jwtToken = jwtService.generateTokenFromUsername(user);
            logger.info("JWT Token: {}", jwtToken);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        logger.info("Authenticated User: {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        logger.info("User Details : {}", userDetails);
        logger.info("Username : {}", userDetails.getUsername());
        var jwtToken = jwtService.generateTokenFromUsername(userDetails);
        return AuthenticationResponse.builder()
                .username(user.getEmail())
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }
}
