package com.alinahamatkhanova.controllers;
import com.alinahamatkhanova.services.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        authService.logout(request, response, authentication);
        return ResponseEntity.ok("logout successful");
    }

    @GetMapping("/success")
    public String loginSuccess() {
        return "login successful";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout successful";
    }
}
