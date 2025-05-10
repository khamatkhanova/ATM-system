package com.alinahamatkhanova.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccessController {

    @GetMapping("/success")
    public String success() {
        return "login successful";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout successful";
    }
}
