package com.alinahamatkhanova.controllers;
import com.alinahamatkhanova.services.ProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {

    private final ProxyService proxyService;

    public ClientController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getClientInfo(Authentication authentication, @RequestHeader Map<String, String> headers) {
        String username = authentication.getName();
        return proxyService.get("/api/users/" + username, headers);
    }

    @GetMapping("/accounts")
    public ResponseEntity<String> getClientAccounts(Authentication authentication, @RequestHeader Map<String, String> headers) {
        String username = authentication.getName();
        return proxyService.get("/api/accounts/user/" + username, headers);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<String> getAccountById(@PathVariable("id") String id, @RequestHeader Map<String, String> headers) {
        return proxyService.get("/api/accounts/" + id, headers);
    }

    @PostMapping("/friends")
    public ResponseEntity<String> addFriend(Authentication authentication, @RequestParam("login") String loginParam, @RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("gender") String gender, @RequestParam("hairColor") String hairColor) {

        String login = authentication.getName();
        String body = """
        {
            "login": "%s",
            "name": "%s",
            "age": %d,
            "gender": "%s",
            "hairColor": "%s"
        }
        """.formatted(loginParam, name, age, gender, hairColor);

        Map<String, String> headers = Map.of("Content-Type", "application/json");
        return proxyService.post("/api/users/" + login + "/friends", body, headers);
    }

    @DeleteMapping("/friends/{friendLogin}")
    public ResponseEntity<String> removeFriend(Authentication authentication, @PathVariable("friendLogin") String friendLogin, @RequestHeader Map<String, String> headers) {

        String login = authentication.getName();
        return proxyService.delete("/api/users/" + login + "/friends/" + friendLogin, headers);
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable("id") String id, @RequestParam("amount") double amount, @RequestHeader Map<String, String> headers) {
        String path = "/api/accounts/" + id + "/deposit?amount=" + amount;
        return proxyService.postWithoutBody(path, headers);
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable("id") String id, @RequestParam("amount") double amount, @RequestHeader Map<String, String> headers) {
        String path = "/api/accounts/" + id + "/withdraw?amount=" + amount;
        return proxyService.postWithoutBody(path, headers);
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<String> transfer(@RequestParam("fromAccountId") String fromAccountId, @RequestParam("toAccountId") String toAccountId, @RequestParam("amount") double amount, @RequestHeader Map<String, String> headers) {
        String path = "/api/accounts/transfer?fromAccountId=" + fromAccountId + "&toAccountId=" + toAccountId + "&amount=" + amount;
        return proxyService.postWithoutBody(path, headers);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("logout");
    }
}