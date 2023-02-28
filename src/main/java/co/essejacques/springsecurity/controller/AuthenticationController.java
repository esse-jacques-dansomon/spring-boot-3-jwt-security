package co.essejacques.springsecurity.controller;

import co.essejacques.springsecurity.auth.AuthenticationRequest;
import co.essejacques.springsecurity.auth.AuthenticationResponse;
import co.essejacques.springsecurity.auth.AuthenticationService;
import co.essejacques.springsecurity.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author  : essejacques.co
 * Date    : 28/02/2023 00:48
 * Project : spring-security
 */

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(
                authenticationService.authenticate(request)
        );

    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world");
    }
}
