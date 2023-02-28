package co.essejacques.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author  : essejacques.co
 * Date    : 28/02/2023 00:48
 * Project : spring-security
 */

@RestController
@RequestMapping("/api/v1/secured")
@RequiredArgsConstructor
public class SecureController {

    @PostMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello secured world");
    }
}
