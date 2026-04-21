package com.alinahamatkhanova.services;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}