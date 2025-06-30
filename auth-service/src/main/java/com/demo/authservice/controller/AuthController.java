package com.demo.authservice.controller;

import com.demo.authservice.dto.AuthRequest;
import com.demo.authservice.dto.AuthResponse;
import com.demo.authservice.dto.RegisterRequest;
import com.demo.authservice.dto.UserInfoDto;
import com.demo.authservice.security.JwtProvider;
import com.demo.authservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(authService.login(authRequest));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<UserInfoDto> validate(@RequestParam String token) {
        String username = jwtProvider.parseUsername(token);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (jwtProvider.validateToken(token, user)) {
            return ResponseEntity.ok(new UserInfoDto(
                    username,
                    jwtProvider.getRolesFromToken(token)
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
