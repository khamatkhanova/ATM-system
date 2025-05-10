package com.alinahamatkhanova.controllers;
import com.alinahamatkhanova.models.Role;
import com.alinahamatkhanova.services.AuthService;
import com.alinahamatkhanova.services.ProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AuthService authService;
    private final PasswordEncoder encoder;
    private final ProxyService proxyService;

    public AdminController(AuthService authService, PasswordEncoder encoder, ProxyService proxyService) {
        this.authService = authService;
        this.encoder = encoder;
        this.proxyService = proxyService;
    }

    @PostMapping("/create-client")
    public ResponseEntity<Void> createClient(@RequestHeader Map<String, String> headers, @RequestBody Map<String, String> body) {
        String username = body.get("username"); String password = body.get("password"); String name = body.get("name"); String gender = body.get("gender"); String hairColor = body.get("hairColor"); String ageStr = body.get("age");

        if (username == null || password == null || name == null || gender == null || hairColor == null || ageStr == null || authService.exists(username)) {
            return ResponseEntity.badRequest().build();
        }
        int age = Integer.parseInt(ageStr);
        authService.addUser(username, password, Role.ROLE_CLIENT, encoder);

        String jsonBody = """
        {
            "login": "%s",
            "name": "%s",
            "age": %d,
            "gender": "%s",
            "hairColor": "%s"
        }
    """.formatted(username, name, age, gender, hairColor);

        proxyService.post("/api/users", jsonBody, Map.of("Content-Type", "application/json"));
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (authService.exists(username)) {
            return ResponseEntity.badRequest().body("user already exists");
        }
        authService.addUser(username, password, Role.ROLE_ADMIN, encoder);
        return ResponseEntity.ok("admin created");
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(@RequestHeader Map<String, String> headers, @RequestParam(name ="gender", required = false) String gender, @RequestParam(name = "hairColor", required = false) String hairColor) {
        StringBuilder path = new StringBuilder("/api/users");
        if (gender != null || hairColor != null) {
            path.append("?");
            if (gender != null) path.append("gender=").append(gender).append("&");
            if (hairColor != null) path.append("hairColor=").append(hairColor);
        }
        return proxyService.get(path.toString(), headers);
    }
    @GetMapping("/users/{username}")
    public ResponseEntity<String> getUserByUsername(@PathVariable("username") String username, @RequestHeader Map<String, String> headers) {
        return proxyService.get("/api/users/" + username, headers);
    }

    @GetMapping("/accounts")
    public ResponseEntity<String> getAllAccounts(@RequestHeader Map<String, String> headers) {
        return proxyService.get("/api/accounts", headers);
    }

    @GetMapping("/accounts/user/{username}")
    public ResponseEntity<String> getAccountsByUser(@PathVariable("username") String username, @RequestHeader Map<String, String> headers) {
        return proxyService.get("/api/accounts/user/" + username, headers);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<String> getAccountWithTransactions(@PathVariable("id") String id, @RequestHeader Map<String, String> headers) {
        return proxyService.get("/api/accounts/" + id, headers);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("logout");}
}