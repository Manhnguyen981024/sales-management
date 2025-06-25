package com.demo.authservice.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.demo.authservice.dto.AuthRequest;
import com.demo.authservice.dto.AuthResponse;
import com.demo.authservice.dto.RegisterRequest;
import com.demo.authservice.entity.Role;
import com.demo.authservice.entity.User;
import com.demo.authservice.repository.RoleRepository;
import com.demo.authservice.repository.UserRepository;
import com.demo.authservice.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock private UserRepository userRepo;
    @Mock private RoleRepository roleRepo;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtProvider jwtProvider;
    @Mock private AuthenticationManager authenticationManager;

    @Test
    void shouldRegisterSuccessfully() {
        RegisterRequest registerRequest = new RegisterRequest("test12", "test12", "123456");

        when(userRepo.existsByUsername("test12")).thenReturn(false);
        when(roleRepo.findByRoleName("ROLE_USER")).thenReturn(Optional.of(new Role(1L , "ROLE_USER")));
        when(passwordEncoder.encode("123456")).thenReturn("123456");

        authService.register(registerRequest);
        verify(userRepo).save(any(User.class));
    }

    @Test
    void shouldLoginAndReturnToken() {
        AuthRequest req = new AuthRequest("john", "1234");

        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("john", "1234", List.of());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtProvider.generateToken(userDetails)).thenReturn("jwt-token");

        AuthResponse res = authService.login(req);

        assertEquals("jwt-token", res.token());
    }
}
