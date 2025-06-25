package com.demo.authservice.service;

import com.demo.authservice.dto.AuthRequest;
import com.demo.authservice.dto.AuthResponse;
import com.demo.authservice.dto.RegisterRequest;
import com.demo.authservice.entity.Role;
import com.demo.authservice.entity.User;
import com.demo.authservice.repository.RoleRepository;
import com.demo.authservice.repository.UserRepository;
import com.demo.authservice.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtProvider.generateToken(userDetails);
            return new AuthResponse(token);
        } catch (BadCredentialsException  e) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.username()))
            throw new RuntimeException("Username is already in use");

        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }
}
