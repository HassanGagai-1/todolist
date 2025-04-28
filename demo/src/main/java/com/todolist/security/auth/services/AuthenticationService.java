package com.todolist.security.auth.services;


import com.todolist.model.User;
import com.todolist.repository.RoleRepository;
import com.todolist.repository.UserRepository;
import com.todolist.security.auth.AuthenticationRequest;
import com.todolist.security.auth.AuthenticationResponse;
import com.todolist.security.auth.RegisterRequest;
import com.todolist.services.impl.JwtService;
import com.todolist.services.impl.TaskServiceImpl;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.todolist.model.Role_name;
import com.todolist.model.Role;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

        public AuthenticationResponse register(RegisterRequest request) {

            Role role = roleRepository.findByRoleName(Role_name.USER)
                    .orElseGet(() -> roleRepository.save(new Role(Role_name.USER)));
            
            logger.info("User Role: {}", role);
            User user = new User();


            user.setRole(role);
            user.setName(request.getName());

            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

    }
}
